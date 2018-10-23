/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

/**
 *
 * @author Arkai
 */
public class Instruction 
{
    private String label;
    private String instruction;
    private String operand;
    private String parameter;

    public Instruction(String label, String instruction, String operand, String parameter) {
        this.label = label;
        this.instruction = instruction;
        this.operand = operand;
        this.parameter = parameter;
    }
    
    

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}

    

 
    
    