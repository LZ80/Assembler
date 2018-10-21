import java.util.*;


public class EnsambladorZ80
{
    public static void main(String args[])
    {
        String[][] instr = new String[3][3];

        instr[0][0] = "hola";


        HashMap<String, String> etiquetas = new HashMap<String, String>();

        HashMap<String, String> variables = new HashMap<String, String>();

        ArrayList<Instruccion> instrucicon = new ArrayList<Instruccion>();

        System.out.println(instr[0][0]);
    }
}