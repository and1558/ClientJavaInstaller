package uk.and1558;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Backend extends Thread
{
    MainGUI installer;
    private static final SimpleDateFormat sdf;
    String mcDir;
    String version;
    boolean legacy;
    public int progress;
    public String message;
    
    public Backend(final MainGUI installer, final String ver, final boolean old, final String minecraftDir) {
        this.mcDir = "";
        this.version = "";
        this.legacy = false;
        this.message = "";
        this.installer = installer;
        this.version = ver;
        this.legacy = old;
        this.mcDir = minecraftDir;
        Backend.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Override
    public void run() {
        final File mainJarDir = new File(this.mcDir, "/versions/and1558/");
        final File mainJar = new File(this.mcDir, "/versions/and1558/and1558.jar");
        final File mainJsonDir = new File(this.mcDir, "/versions/and1558/");
        final File mainJson = new File(this.mcDir, "/versions/and1558/and1558.json");
        if (!this.legacy) {
        	final int msgRes = JOptionPane.showConfirmDialog(null, "Are you sure you want to install this version?\nVersion selected: " + this.version, "Continue?", 0);
            if (msgRes == 0) {
                this.setProgress(0, "Main Thread - Starting Installer Thread");
                final String verDirStr = this.mcDir;
                final File baseDir = new File(verDirStr);
                this.setProgress(5, " ThreadInstaller - Deleting old version");
                Utils.deleteDirectory(baseDir);
                this.setProgress(21, "Installer Thread - Creating folder");
                if (!baseDir.exists()) {
                    baseDir.mkdirs();
                }
                final File mainLibDir = new File(this.mcDir, "/libraries/uk/and1558/AND1558/LOCAL/");
                final File mainOfLibDir = new File(this.mcDir, "/libraries/cc/hyperium/OptiFine/LOCAL/");
                final File mainWrapperLibDir = new File(this.mcDir, "/libraries/net/minecraft/launchwrapper/1.11/");
                final File mainLib = new File(this.mcDir, "/libraries/uk/and1558/AND1558/LOCAL/AND1558-LOCAL.jar");
                final File mainOfLib = new File(this.mcDir, "/libraries/cc/hyperium/OptiFine/LOCAL/Optifine-LOCAL.jar");
                final File mainWrapperLib = new File(this.mcDir, "/libraries/net/minecraft/launchwrapper/1.11/launchwrapper-1.11.jar");
                final String mainLibDirStr = this.mcDir;
                final String ofLibDirStr = this.mcDir;
                final String lwrapperLibDirStr = this.mcDir;
                Utils.deleteDirectory(mainOfLibDir);
                Utils.deleteDirectory(mainWrapperLibDir);
                Utils.deleteDirectory(mainLibDir);
                this.setProgress(26, mainLibDirStr);
                if(!mainJarDir.exists()) {
                	mainJarDir.mkdirs();
                }
                if (!mainLibDir.exists()) {
                    mainLibDir.mkdirs();
                }
                this.setProgress(27, ofLibDirStr);
                if (!mainOfLibDir.exists()) {
                    mainOfLibDir.mkdirs();
                }
                this.setProgress(28, lwrapperLibDirStr);
                if (!mainWrapperLibDir.exists()) {
                    mainWrapperLibDir.mkdirs();
                }
                try {
                    this.setProgress(45, "Installer Thread - Downloading Vanilla 1.8.9 Jar File");
                    Utils.writeFile(Constant.getVanillaJar(), mainJar);
                    this.setProgress(47, "Installer Thread - Downloading Modified JSON File");
                    Utils.writeFile(Constant.getJson(this.version), mainJson);
                    this.setProgress(65, "Installer Thread - Downloading Client Jar");
                    Utils.writeFile(Constant.getNewJar(this.version), mainLib);
                    this.setProgress(78, "Installer Thread - Downloading Optifine Jar");
                    Utils.writeFile(Constant.getOptifineJar(), mainOfLib);
                    this.setProgress(89, "Installer Thread - Downloading Launchwrapper");
                    Utils.writeFile(Constant.getLaunchwrapperJar(), mainWrapperLib);
                    this.setProgress(100, "Installer Thread - Done!");
                }
                catch (IOException ex) {
                    Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else {
            	this.installer.jProgressBar1.setVisible(false);
                this.installer.jLabel4.setVisible(false);
            }
            return;
        }
        if (this.legacy) {
            final int msgRes = JOptionPane.showConfirmDialog(null, "WARNING: The version " + this.version + " is a legacy version and WILL NOT be updated!\nAre you sure you want to continue?", "Continue?", 0, JOptionPane.WARNING_MESSAGE);
            if (msgRes == 0) {
                this.setProgress(0, "Main Thread - Starting Installer Thread");
                final File verDir = new File(this.mcDir, "versions/and1558/");
                this.setProgress(12, "ThreadInstaller - Deleting old version");
                Utils.deleteDirectory(verDir);
                if (!verDir.exists()) {
                    verDir.mkdirs();
                }
                try {
                    this.setProgress(56, "Installer Thread - Downloading Client Jar");
                    Utils.writeFile(Constant.getLegacyJar(this.version), new File(verDir + "/and1558.jar"));
                    this.setProgress(99, "Installer Thread - Downloading Client Json");
                    Utils.writeFile(Constant.getJson(this.version), new File(verDir + "/and1558.json"));
                    this.setProgress(100, "Main Thread - Done");
                }
                catch (IOException ex2) {
                    Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }else {
            	this.installer.jProgressBar1.setVisible(false);
                this.installer.jLabel4.setVisible(false);
            }
        }
    }
    
    public void setProgress(final int progress, final String message) {
        this.installer.jProgressBar1.setValue(progress);
        this.installer.jLabel4.setText(message);
    }
    
    static {
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    }
}
