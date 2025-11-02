/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory_office_supplies;

import Database_config.DbConnection;

/**
 *
 * @author luisr
 */
public class Inventory_office_supplies {


    public static void main(String[] args) {
        DbConnection db = new DbConnection();
        new SplashScreen().setVisible(true);
        
    }
    
}
