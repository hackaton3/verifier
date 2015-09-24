package com.ericsson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Repository;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Repository
public class SSHUtil {
    private String host;
    private String user;
    private String password;

    public String runCommand(String command) {
        try{

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            //System.out.println("Connected");

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(null);

            InputStream in = null;
            InputStream err = null;
            in = channel.getInputStream();
            err = ((ChannelExec)channel).getErrStream();

            channel.connect();
            StringBuffer result = new StringBuffer();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0) {
                        break;
                    }
                    String tmp2 = new String(tmp, 0, i);
                    System.out.print(tmp2);
                    result.append(tmp2);
                }
                while(err.available()>0){
                    int i=err.read(tmp, 0, 1024);
                    if(i<0) {
                        break;
                    }
                    String tmp2 = new String(tmp, 0, i);
                    System.out.print(tmp2);
                    result.append(tmp2);
                }
                if(channel.isClosed()){
                    //System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
            //System.out.println("DONE");
            return result.toString();
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void putFile(String in_localFileToPut, String in_remoteFileToPut){
        boolean ptimestamp = true;
        FileInputStream fis=null;

        String localFileToPut = FilenameUtils.separatorsToSystem(in_localFileToPut);
        String remoteFileToPut = FilenameUtils.separatorsToUnix(in_remoteFileToPut);


        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            //System.out.println("Connected");

            // exec 'scp -t rfile' remotely
            String command="scp " + (ptimestamp ? "-p" :"") +" -t "+ remoteFileToPut;
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out=channel.getOutputStream();
            InputStream in=channel.getInputStream();

            channel.connect();

            if(checkAck(in)!=0){
                System.exit(0); // should be changed
            }

            File _lfile = new File(localFileToPut);

            if(ptimestamp){
                command="T "+_lfile.lastModified()/1000+" 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command+=" "+_lfile.lastModified()/1000+" 0\n";
                out.write(command.getBytes()); out.flush();
                if(checkAck(in)!=0){
                    System.exit(0);
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize=_lfile.length();
            command="C0644 "+filesize+" ";
            if(localFileToPut.lastIndexOf(File.separatorChar)>0){
                command+=localFileToPut.substring(localFileToPut.lastIndexOf(File.separatorChar)+1);
            }
            else{
                command+=localFileToPut;
            }
            command+="\n";
            out.write(command.getBytes()); out.flush();
            if(checkAck(in)!=0){
                System.exit(0);
            }

            // send a content of localFileToPut
            fis=new FileInputStream(localFileToPut);
            byte[] buf=new byte[1024];
            while(true){
                int len=fis.read(buf, 0, buf.length);
                if(len<=0) {
                    break;
                }
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis=null;
            // send '\0'
            buf[0]=0; out.write(buf, 0, 1); out.flush();
            if(checkAck(in)!=0){
                System.exit(0);
            }
            out.close();

            channel.disconnect();
            session.disconnect();

            System.out.println("SSHPutFile successful.");
            //System.exit(0);

        } catch (JSchException e) {
            System.out.println("Caught JSchException:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(Exception e){
            System.out.println("Caught another exception in SSHPutFile:");
            System.out.println(e);
            try{if(fis!=null) {
                fis.close();
            }}catch(Exception ee){}
            throw new RuntimeException(e);
        }
    } //putFile

    public void getFile(String remoteFile, String localFile) throws Exception{

        //Verify the file is there before trying to fetch it
        String commandReturn01;
        String fileNotFound = "No such file or directory";

        String remoteFileToGet=FilenameUtils.separatorsToUnix(remoteFile);
        String localFileToGet=FilenameUtils.separatorsToSystem(localFile);


        commandReturn01 = runCommand("ls " + remoteFileToGet );
        if (commandReturn01.contains(fileNotFound)){
            throw new Exception("Could not locate remoteFileToGet: " + remoteFileToGet + ". Abort!");
        }

        FileOutputStream fos=null;

        try {
            String prefix=null;
            if(new File(localFileToGet).isDirectory()){
                prefix=localFileToGet+File.separator;
            }

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            //System.out.println("Connected");

            // exec 'scp -f rfile' remotely
            String command="scp " + " -f "+ remoteFileToGet;
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out=channel.getOutputStream();
            InputStream in=channel.getInputStream();

            channel.connect();

            byte[] buf=new byte[1024];

            // send '\0'
            buf[0]=0; out.write(buf, 0, 1); out.flush();

            while(true){
                int c=checkAck(in);
                if(c!='C'){
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize=0L;
                while(true){
                    if(in.read(buf, 0, 1)<0){
                        // error
                        break;
                    }
                    if(buf[0]==' ') {
                        break;
                    }
                    filesize=filesize*10L+(buf[0]-'0');
                }

                String file=null;
                for(int i=0;;i++){
                    in.read(buf, i, 1);
                    if(buf[i]==(byte)0x0a){
                        file=new String(buf, 0, i);
                        break;
                    }
                }

                System.out.println("filesize="+filesize+", file="+file);

                // send '\0'
                buf[0]=0; out.write(buf, 0, 1); out.flush();

                // read a content of localFileToGet
                fos=new FileOutputStream(prefix==null ? localFileToGet : prefix+file);
                int foo;
                while(true){
                    if(buf.length<filesize) {
                        foo=buf.length;
                    } else {
                        foo=(int)filesize;
                    }
                    foo=in.read(buf, 0, foo);
                    if(foo<0){
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize-=foo;
                    if(filesize==0L) {
                        break;
                    }
                }
                fos.close();
                fos=null;

                if(checkAck(in)!=0){
                    System.exit(0);
                }

                // send '\0'
                buf[0]=0; out.write(buf, 0, 1); out.flush();
            }

            session.disconnect();

            System.out.println("SSHGetFile successful.");
            //System.exit(0);

        } catch (JSchException e) {
            System.out.println("Caught JSchException:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(Exception e){
            System.out.println("Caught another exception in SSHGetFile:");
            System.out.println(e);
            try{if(fos!=null) {
                fos.close();
            }}catch(Exception ee){}
            throw new RuntimeException(e);
        }
    } //SSHGetFile

    static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        // 1 for error,
        // 2 for fatal error,
        // -1
        if (b == 0) {
            return b;
        }
        if (b == -1) {
            return b;
        }

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    public boolean waitUntilFileExist(String filename, long timeoutInSeconds) throws InterruptedException {
        boolean isPass = false;
        long i = 0;
        System.out.println(String.format("Wait until %s exists. Time out = %d", filename, timeoutInSeconds));
        for (i = 0; i < timeoutInSeconds; i+=2) {
            String result = runCommand("ls -la " + filename);
            if (result.contains("No such file or directory")) {
                System.out.println(i + ": Wait 2 seconds ...");
                Thread.sleep(2000);
            }
            else {
                isPass = true;
                break;
            }
        }
        System.out.println(String.format("Result: %s. Spend %s seconds totally.", isPass, i));
        return isPass;
    }

    public static void main(String[] args) {
        SSHUtil a = new SSHUtil();
        a.setHost("10.116.5.99");
        a.setPassword("root1234");
        a.setUser("root");
        a.runCommand("ls /var/lib/nobody/testautomation/PublisherXmltv/PublisherXmltv.xml");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}