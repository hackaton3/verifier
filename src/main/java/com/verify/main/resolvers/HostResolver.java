/**
 * 
 */
package com.verify.main.resolvers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.verify.main.util.SSHUtil;
import com.verify.main.verifyobjs.Host;

/**
 * @author Simon Meng
 * 
 */
public class HostResolver {

	public static String IP_RGX = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

	
	public static List<Host> resoveRemoteHost(String ip, String user, String passwd) {
		List<Host> hosts = new ArrayList<Host>();
		String tempFile = System.getProperty("java.io.tmpdir") + System.nanoTime() + "-etc-hosts"; 
		SSHUtil sshClient = new SSHUtil(); 
		sshClient.setHost(ip);
		sshClient.setUser(user);
		sshClient.setPassword(passwd);
		try {
			sshClient.getFile("/etc/hosts", tempFile);
			return resoveHost(tempFile); 
		} catch (Exception e) {
			//FIXME:
			e.printStackTrace();
		} finally {
			FileUtils.deleteQuietly(new File(tempFile)); 
		}
		return hosts; 
	}
	
	
	public static List<Host> resoveHost(String file) {
		List<Host> hosts = new ArrayList<Host>();
		InputStream in = null; 
		try {
			in = new FileInputStream(new File(file));
			List<String> lines = IOUtils.readLines(in);
			for (String line : lines) {
				if (StringUtils.isNotBlank(line)) {
					String[] strs = line.trim().split("(\\s)+");
					if (strs.length > 1 && strs[0].matches(IP_RGX)) {
						for (int i = 1; i < strs.length; i++) {
							hosts.add(new Host(strs[i]));
							System.out.println(strs[i]);
						}
					}
				}
			}
		} catch (IOException e) {
			//FIXME
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return hosts;
	}
}
