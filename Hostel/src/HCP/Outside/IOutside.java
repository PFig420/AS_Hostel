/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package HCP.Outside;

/**
 *
 * @author omp
 */
public interface IOutside extends IOutside_Porter, IOutside_Customer {

    void nextSimulation(int i);
    void setMode();
    void advanceToNextStep();

    public void suspend();

    public void restart();
    
}
