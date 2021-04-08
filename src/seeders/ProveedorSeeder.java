package seeders;

import factories.ProveedoresFactory;
import modelos.Proveedor;
import modelos.Rubro;
import modelos.enums.TipoIVA;
import servicios.ProveedoresService;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProveedorSeeder {

    public static void run() throws Exception {

        Proveedor p1 = ProveedoresFactory.create(
                "Proveedor Uno SRL",
                "El Primer Proveedor",
                "30987652331",
                "proveedor1@mail.com",
                "1133224450",
                TipoIVA.RESPONSABLE_INSCRIPTO,
                LocalDate.now(),
                "30987652331",
                100000.00,
                new ArrayList<Rubro>()
        );

        Proveedor p2 = ProveedoresFactory.create(
                "Proveedor Dos SRL",
                "El Segundo Proveedor",
                "30987652332",
                "proveedor2@mail.com",
                "1133224451",
                TipoIVA.RESPONSABLE_INSCRIPTO,
                LocalDate.now(),
                "30987652332",
                100000.00,
                new ArrayList<Rubro>()
        );

        ProveedoresService ps = ProveedoresService.getInstance();
        ps.agregar(p1);
        ps.agregar(p2);
    }
}
