package factories;

import modelos.Proveedor;
import modelos.Rubro;
import modelos.enums.TipoIVA;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProveedoresFactory {

    public static Proveedor create(String razonSocial,
                                   String nombreFantasia,
                                   String cuit,
                                   String email,
                                   String telefono,
                                   TipoIVA tipoIVA,
                                   LocalDate inicioActividad,
                                   String ingresosBrutos,
                                   Double limiteCtaCte,
                                   ArrayList<Rubro> rubros){

        Proveedor proveedor = new Proveedor();

        proveedor.setRazonSocial(razonSocial);
        proveedor.setNombreFantasia(nombreFantasia);
        proveedor.setCuit(cuit);
        proveedor.setEmail(email);
        proveedor.setTelefono(telefono);
        proveedor.setTipoIVA(tipoIVA);
        proveedor.setInicioActividad(inicioActividad);
        proveedor.setIngresosBrutos(ingresosBrutos);
        proveedor.setLimiteCtaCte(limiteCtaCte);
        proveedor.setRubro(rubros);

        return proveedor;
    }

}
