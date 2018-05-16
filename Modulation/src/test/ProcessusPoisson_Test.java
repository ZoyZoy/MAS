/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import simulation.ProcessusPoisson;

/**
 *
 * @author jerem
 */
public class ProcessusPoisson_Test {
    public static void main(final String[] args) throws InterruptedException {
        // final ProcessusPoisson demo = new ProcessusPoisson(2, 0.01, 50, 1, 1000000);
        ProcessusPoisson procPoisson = new ProcessusPoisson(1, 1, 0.007, 1, 20000);
    }
}
