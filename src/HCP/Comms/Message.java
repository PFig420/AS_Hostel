/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Comms;

import java.io.Serializable;

/**
 *
 * @author omp
 */
public class Message implements Serializable, IMessage{
    private Message() {
    }
    public static IMessage getInstance() {
        return new Message();
    }
}
