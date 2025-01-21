package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class PedidoDAOImpl implements PedidoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(
                        rs.getInt("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_comercial"))
        );
        log.info("Lista de pedidos: {}", pedidos);
        return pedidos;
    }

    @Override
    public void create(Pedido pedido) {

    }

    @Override
    public void deleteByClienteID(long id) {

    }

    @Override
    public Optional<Pedido> buscarPedidoPorId(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Pedido pedido) {

    }

    @Override
    public List<Pedido> pedidoIDCliente(int id_cliente) {
        return List.of();
    }
}
