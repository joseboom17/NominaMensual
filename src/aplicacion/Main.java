package aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private static final ArrayList<Empleado> empleados = new ArrayList<>();

    public static void main(String[] args) {

        int numeroMes = solicitarMes();

        int opcion;

        do {
            limpiarPantalla();

            System.out.println("/////////////////////////////////////");
            System.out.println("       NOMINA DEL MES: "
                    + meses[numeroMes - 1].toUpperCase());
            System.out.println("/////////////////////////////////////");
            System.out.println();
            System.out.println("1 - Ingreso de nomina");
            System.out.println("2 - Impresion de nomina");
            System.out.println("3 - Salir");
            System.out.println();

            opcion = leerEntero("Seleccione una opcion: ", 1, 3);

            switch (opcion) {

                case 1:
                    ingresarNomina(numeroMes);
                    break;

                case 2:
                    imprimirNomina(numeroMes);
                    break;

                case 3:
                    limpiarPantalla();
                    System.out.println("Gracias por utilizar el sistema de nomina.");
                    System.out.println("Programa finalizado.");
                    break;
            }

        } while (opcion != 3);

        teclado.close();
    }

    
    private static int solicitarMes() {

        limpiarPantalla();

        System.out.println("/////////////////////////////////////");
        System.out.println("          CALCULO DE NOMINA");
        System.out.println("/////////////////////////////////////");

        return leerEntero("Ingrese el mes a trabajar (1-12): ", 1, 12);
    }

   
    private static void ingresarNomina(int numeroMes) {

        int opcionTipo;
        int continuar;

        do {
            limpiarPantalla();

            System.out.println("NOMINA DEL MES: "
                    + meses[numeroMes - 1].toUpperCase());
            System.out.println("INGRESO DE EMPLEADO A NOMINA");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            System.out.println();

            opcionTipo = leerEntero(
                    "Seleccione el tipo de empleado: ", 1, 2);

            if (opcionTipo == 1) {
                registrarEmpleadoFijo(numeroMes);
            } else {
                registrarEmpleadoPorHora(numeroMes);
            }

            System.out.println();
            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();

            continuar = leerEntero(
                    "¿Desea ingresar otro empleado?\n"
                    + "1 - Si, ingresar otro empleado\n"
                    + "2 - Volver al menu principal\n"
                    + "Seleccione una opcion: ", 1, 2);

        } while (continuar == 1);
    }

    
    private static void registrarEmpleadoFijo(int numeroMes) {

        limpiarPantalla();

        System.out.println("NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());
        System.out.println("INGRESO DE EMPLEADO FIJO");
        System.out.println();

        String dpi = solicitarDPI();
        String nombre = solicitarNombre();

        double sueldoMensual = leerDecimalNoNegativo(
                "Sueldo mensual (Q): ");

        double bonificacion = leerDecimalNoNegativo(
                "Bonificacion de este mes (Q): ");

        EmpleadoFijo empleado = new EmpleadoFijo(
                dpi,
                nombre,
                sueldoMensual,
                bonificacion
        );

        empleados.add(empleado);

        System.out.printf(
                Locale.US,
                "%nPago del mes = sueldo mensual + bonificacion%n"
                + "Pago de %s = Q %.2f%n",
                empleado.getNombre(),
                empleado.calcularPago()
        );
    }

    
    private static void registrarEmpleadoPorHora(int numeroMes) {

        limpiarPantalla();

        System.out.println("NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());
        System.out.println("INGRESO DE EMPLEADO POR HORA");
        System.out.println();

        String dpi = solicitarDPI();
        String nombre = solicitarNombre();

        double pagoPorHora = leerDecimalNoNegativo(
                "Pago por hora (Q): ");

        double horasTrabajadas = leerDecimalNoNegativo(
                "Horas trabajadas este mes: ");

        EmpleadoPorHora empleado = new EmpleadoPorHora(
                dpi,
                nombre,
                pagoPorHora,
                horasTrabajadas
        );

        empleados.add(empleado);

        System.out.printf(
                Locale.US,
                "%nPago del mes = pago por hora x horas trabajadas%n"
                + "Pago de %s = Q %.2f%n",
                empleado.getNombre(),
                empleado.calcularPago()
        );
    }

    
    private static void imprimirNomina(int numeroMes) {

        limpiarPantalla();

        System.out.println("/////////////////////////////////////");
        System.out.println("              NOMINA DEL MES: "
                + meses[numeroMes - 1].toUpperCase());
        System.out.println("/////////////////////////////////////");
        System.out.println();

        double totalNomina = 0;

        if (empleados.isEmpty()) {

            System.out.println(
                    "No hay empleados registrados en la nomina de este mes.");

        } else {

            System.out.printf(
                    "%-16s %-22s %-12s %12s%n",
                    "DPI", "Nombre", "Tipo", "Pago (Q)");

            System.out.println(
                    "--------------------------------------------------------------");

            for (Empleado empleado : empleados) {

                double pago = empleado.calcularPago();

                totalNomina += pago;

                System.out.printf(
                        Locale.US,
                        "%-16s %-22s %-12s Q %10.2f%n",
                        empleado.getDpi(),
                        empleado.getNombre(),
                        empleado.obtenerTipo(),
                        pago
                );
            }
        }

        System.out.println();
        System.out.println("--------------------------------------------------------------");
        System.out.println("Total de trabajadores: " + empleados.size());

        System.out.printf(
                Locale.US,
                "Total de nomina: Q %.2f%n",
                totalNomina
        );

        System.out.println("--------------------------------------------------------------");

        System.out.println();
        System.out.print("Presione Enter para volver al menu principal...");
        teclado.nextLine();
    }

    
    private static String solicitarDPI() {

        String dpi;

        while (true) {

            System.out.print("DPI: ");
            dpi = teclado.nextLine().trim();

            if (dpi.matches("[0-9]{13}")) {
                return dpi;
            }

            System.out.println(
                    "Error: el DPI debe contener exactamente 13 digitos.");
        }
    }

    // Solicitar y validar nombre
    private static String solicitarNombre() {

        String nombre;

        while (true) {

            System.out.print("Nombre: ");
            nombre = teclado.nextLine().trim();

            if (!nombre.isEmpty()) {
                return nombre;
            }

            System.out.println(
                    "Error: el nombre no puede estar vacio.");
        }
    }

   
    private static int leerEntero(
            String mensaje, int minimo, int maximo) {

        while (true) {

            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();

            try {

                int numero = Integer.parseInt(entrada);

                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }

                System.out.println(
                        "Error: ingrese un numero entre "
                        + minimo + " y " + maximo + ".");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un numero entero.");
            }
        }
    }

    
    private static double leerDecimalNoNegativo(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim();

            try {

                double numero = Double.parseDouble(entrada);

                if (Double.isFinite(numero) && numero >= 0) {
                    return numero;
                }

                System.out.println(
                        "Error: ingrese un numero finito mayor o igual a cero.");

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un valor numerico valido.");
            }
        }
    }

    
    private static void limpiarPantalla() {

        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}