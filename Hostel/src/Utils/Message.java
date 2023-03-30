/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author pedro
 */
public class Message implements  Serializable,IMessage {
    private static final long serialVersionUID = 2023L;

    private final String code;
    private final int[] value;
      
    private Message( Message msg ) {
        this.code = msg.code;
        this.value = msg.value;
    }
    private Message(String code, int[] value) {
        this.code = code;
        this.value = value;
    }
    public static IMessage getInstance( String code, int[] value ) {
        return new Message( code, value );
    }
    public static  IMessage getInstance( Message msg ) {
        return new Message( msg );
    }  
    @Override
    public String code() {
        return code;
    }
    @Override
    public int[] value() {
        return value;
    }
    @Override
    public void print( String text ) {
        System.out.println(text + ": (" + code + ", " + Arrays.toString(value) + ")");
    }

    @Override
    public void isFull(int customerId) {
        System.out.println("Queue is Full. Last customer is: "+customerId );
    }
}
