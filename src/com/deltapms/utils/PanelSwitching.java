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
public class PanelSwitching {
    private final Stack<String> panelStack = new Stack<>();
    private final MainApplicationFrame mainApp;

    public PanelSwitching(MainApplicationFrame mainApp) {
        this.mainApp = mainApp;
        // Initial panel
        panelStack.push("MainApplicationFrame"); 
    }

    public void pushPanel(String panelName) {
        // Only push if different
        if (panelStack.isEmpty() || !panelStack.peek().equals(panelName)) {
            panelStack.push(panelName);
        }
    }

    public void returnPanel() {
        // We need at least 2 items
        if (panelStack.size() > 1) {
            try {
                panelStack.pop(); // Remove current panel
                String previousPanel = panelStack.peek(); // Look at the one under it
                mainApp.showPanel(previousPanel);
            } catch (Exception e) {
                System.err.println("Error returning to panel: " + e.getMessage());
            }
        } else {
            System.out.println("Already at the root panel.");
        }
    }
}
