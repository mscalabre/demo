package org.update4j.demo.bootstrap;

public class DownloadOrLaunchBootstrap {
    public static void main(String[] args) throws Throwable {
        boolean launch = args.length>0 && args[0].equals("launch");
        if(launch) {
            org.update4j.Bootstrap.main(args);
        }else{
            System.out.println("Just download");
        }
    }
}
