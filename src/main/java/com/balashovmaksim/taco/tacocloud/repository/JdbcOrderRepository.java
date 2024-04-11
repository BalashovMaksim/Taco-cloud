package com.balashovmaksim.taco.tacocloud.repository;

import com.balashovmaksim.taco.tacocloud.model.IngredientRef;
import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into taco_order (delivery_name, delivery_street, delivery_city, " +
                        "delivery_state, delivery_zip, cc_number, cc_expiration, cc_cvv, placed_at) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                tacoOrder.getDeliveryName(),
                                tacoOrder.getDeliveryStreet(),
                                tacoOrder.getDeliveryCity(),
                                tacoOrder.getDeliveryState(),
                                tacoOrder.getDeliveryZip(),
                                tacoOrder.getCcNumber(),
                                tacoOrder.getCcExpiration(),
                                tacoOrder.getCcCVV(),
                                tacoOrder.getPlacedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc,keyHolder);
        long orderId = keyHolder.getKey().longValue();
        tacoOrder.setId(orderId);

        List<Taco> tacos = tacoOrder.getTacos();
        for (Taco taco: tacos){
            saveTaco(orderId,taco);
        }
        return tacoOrder;
    }

    private long saveTaco(Long orderId, Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                  "insert into taco "
                        + "(name, taco_order, created_at) "
                        + "values (?, ?, ?) RETURNING id",
                        Types.VARCHAR, Type.LONG, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                orderId,
                                taco.getCreatedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc,keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        List<IngredientRef> ingredientRefs = taco.getIngredients().stream()
                .map(ingredient -> new IngredientRef(ingredient.getId()))
                .collect(Collectors.toList());
        saveIngredientRefs(tacoId, ingredientRefs);

        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs){
        for (IngredientRef ingredientRef : ingredientRefs){
            jdbcOperations.update(
                    "insert into ingredient_ref (ingredient, taco) "
                    + "values (?,?)",
                    ingredientRef.getIngredient(), tacoId);
        }
    }
}
