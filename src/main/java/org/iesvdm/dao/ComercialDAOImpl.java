package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public synchronized void create(Comercial comercial) {
		// TODO Auto-generated method stub

		String sqlInsert = """
							INSERT INTO comercial (nombre, apellido1, apellido2, comisión) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setFloat(idx, comercial.getComision());
			return ps;
		},keyHolder);

		comercial.setId(keyHolder.getKey().intValue());

		//Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							cliente.getNombre(),
//							cliente.getApellido1(),
//							cliente.getApellido2(),
//							cliente.getCiudad(),
//							cliente.getCategoria()
//					);

		log.info("Insertados {} registros.", rows);

	}

	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getFloat("comisión"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {
		// TODO Auto-generated method stub

		Comercial fab =  jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
						, (rs, rowNum) -> new Comercial(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getFloat("comisión"))
						, id
				);

		if (fab != null) {
			return Optional.of(fab);}
		else {
			log.info("Cliente no encontrado.");
			return Optional.empty(); }


	}

	@Override
	public void update(Comercial comercial) {
		// TODO Auto-generated method stub

		int rows = jdbcTemplate.update("""
						Update comercial SET
                     			nombre = ?,
               					apellido1 = ?,
         						apellido2 = ?,
   								comisión = ?
							WHERE id = ?""",
				comercial.getNombre()
				,comercial.getApellido1()
				,comercial.getApellido2()
				,comercial.getComision()
				,comercial.getId());

		log.info("Devueltos {} registros.", rows);

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		int rows1 = jdbcTemplate.update("DELETE FROM pedido WHERE id_comercial=?", id);
		int rows2 = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Pedido con {} registros eliminados.", rows1);
		log.info("Delete de Comercial con {} registros eliminados.", rows2);
	}

}
