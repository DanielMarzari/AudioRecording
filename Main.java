/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiolistener;

/**
 *
 * @author Daniel
 */
public class Main{
    static recordAudio ra = new recordAudio();
    public static void main(String[] args) {
        try{
            ra.testCapture();
        }catch(Exception e){}
    }
    
}
