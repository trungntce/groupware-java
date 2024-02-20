package kr.co.hs.common.util;

public class ProjectUtils {
	public final static String MAIN_PROJECT_NAME = "/ps";


	public static String getPorjectPath(){
		String path = ProjectUtils.class.getResource("/").getPath();
		String projectPath = null;
		if(path.indexOf(".metadata")>=0){
			projectPath = path.substring(0, path.lastIndexOf(".metadata")-1)+ ProjectUtils.MAIN_PROJECT_NAME;
		}else{
			projectPath = path.substring(0, path.lastIndexOf(ProjectUtils.MAIN_PROJECT_NAME) + ProjectUtils.MAIN_PROJECT_NAME.length());
		}
		return projectPath;
	}

	public static boolean isWindows(){
		String osName = System.getProperty("os.name");
		if(osName.indexOf("Windows") >= 0){
			return true;
		}else{
			return false;
		}
	}

	public static String osName(){
		return System.getProperty("os.name");
	}
}
