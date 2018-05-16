/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loismathematiques;

import java.util.Random;

/**
 *
 * @author al_fa_000
 */
public class Exponentielle extends LoisMathematiques{
    double lambda;
    
    public Exponentielle(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double genererNombreSuivantLoi() {
        return -(1 / lambda) * Math.log( 1 - new Random().nextDouble());
    }

    @Override
    public double pQueXSoitInferieurA(double x) {
        return 1-Math.exp(-lambda*x);
    }
}
