/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Utils;

/**
 *
 * @author pedro
 */
public interface IMessage {
    String code();
    void print(String text);
    int[] value();
    void isFull(int customerId);
}
