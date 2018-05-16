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
public class Uniforme extends LoisMathematiques{

    int n;
    
    public Uniforme(int n) {
        this.n = n;
    }
    
    @Override
    public double genererNombreSuivantLoi() {
        return new Random().nextInt(n);
    }
    
    @Override
    public double pQueXSoitInferieurA(double x) {
        return x/n;
    }  
}
