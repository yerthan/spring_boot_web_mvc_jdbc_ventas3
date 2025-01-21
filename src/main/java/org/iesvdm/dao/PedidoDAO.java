package org.iesvdm.dao;

import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {

    public List<Pedido> listarPedidos();

    public void create(Pedido pedido);

    public void deleteByClienteID(long  id);
    public Optional<Pedido> buscarPedidoPorId(long id);
    public void update(Pedido pedido);
    public List<Pedido> pedidoIDCliente(int id_cliente);

}
