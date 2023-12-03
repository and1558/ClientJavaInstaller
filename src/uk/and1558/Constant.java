package uk.and1558;

import java.io.IOException;
import java.io.InputStream;

public class Constant {
	public static InputStream getLegacyJar(final String ver) throws IOException {
        System.out.println(ver);
        return Utils.getStreamFromUrl(ver);
    }
    
    public static InputStream getVanillaJar() throws IOException {
        System.out.println("GETTING https://launcher.mojang.com/v1/objects/3870888a6c3d349d3771a3e9d16c9bf5e076b908/client.jar");
        return Utils.getStreamFromUrl("https://launcher.mojang.com/v1/objects/3870888a6c3d349d3771a3e9d16c9bf5e076b908/client.jar");
    }
    
    public static InputStream getNewJar(final String ver) throws IOException {
    	System.out.println("GETTING https://raw.githubusercontent.com/aydenfurr/ClientUpdates/main/" + ver + "/and1558-1.0-all.jar");
        return Utils.getStreamFromUrl("https://raw.githubusercontent.com/aydenfurr/ClientUpdates/main/" + ver + "/and1558-1.0-all.jar");
    }
    
    public static InputStream getOptifineJar() throws IOException {
        System.out.println("GETTING https://raw.githubusercontent.com/and1558/ClientUpdates/main/depends/OptiFine-LOCAL.jar");
        return Utils.getStreamFromUrl("https://raw.githubusercontent.com/and1558/ClientUpdates/main/depends/OptiFine-LOCAL.jar");
    }
    
    public static InputStream getLaunchwrapperJar() throws IOException {
        System.out.println("GETTING https://raw.githubusercontent.com/and1558/ClientUpdates/main/depends/launchwrapper-1.11.jar");
        return Utils.getStreamFromUrl("https://raw.githubusercontent.com/and1558/ClientUpdates/main/depends/launchwrapper-1.11.jar");
    }
    
    public static InputStream getJson(final String ver) throws IOException {
        System.out.println("GETTING https://raw.githubusercontent.com/aydenfurr/ClientUpdates/main/" + ver + "/and1558.json");
        return Utils.getStreamFromUrl("https://raw.githubusercontent.com/aydenfurr/ClientUpdates/main/" + ver + "/and1558.json");
    }
}
