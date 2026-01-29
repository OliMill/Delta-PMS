/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deltapms.utils;
import deltapmsprototype.ui.MainApplicationFrame;
import java.util.Stack;

/**
 *
 * @author 4-omillard
 */
public class PanelSwitching{
    // Declare the stack
    private final Stack<String> panelStack = new Stack<>();
    private final MainApplicationFrame MainApplication1;
    // Use a constructor to set the initial state
    public PanelSwitching(MainApplicationFrame MainApplication) {
        panelStack.push("MainApplicationFrame");
        this.MainApplication1 = MainApplication;
    }

    // Add a method to handle future pushes
    public void pushPanel(String panelName) {
        panelStack.push(panelName);
    }
    public void returnPanel(){
        try{
            panelStack.pop();
            MainApplication1.showPanel(panelStack.peek());
        } catch(Exception e){
            System.out.println("Panel return error");
        }
    }
}
