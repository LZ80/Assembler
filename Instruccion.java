

public class Instruccion
{
    private String etiqueta;
    private String operacion;
    private String parameters;



    public Instruccion(String e, String o, String p)
    {
        this.etiqueta = e;
        this.operacion = o;
        this.parameters = p;
    }

    public String getEtiqueta()
    {
        return this.etiqueta;
    }

    public void setEtiqueta(String e)
    {
        this.etiqueta = e;
    }

    public String getOperacion()
    {
        return this.operacion;
    }

    public void setOperacion(String a)
    {
        this.operacion = a;
    }

    public String getParameters()
    {
        return this.parameters;
    }

    public void setParameters(String p)
    {
        this.parameters = p;
    }
}