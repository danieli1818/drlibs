package drlibs.utils.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ParsingUtils {

	/**
	 * Splits the args to arguments by spaces, ', " excluding escaping characters
	 * like \' and \" and returns the arguments as a String array.
	 * 
	 * @param args The String args to split to its arguments
	 * @return The arguments of the args String
	 */
	public static String[] splitArgs(String args) {
		Scanner scanner = new Scanner(args);
		Pattern pattern = Pattern
				.compile("((?<= )|^)((([^ '\"][^ ]*?)|('.*?(?<!\\\\)')|(\".*?(?<!\\\\)\"))((?= )|$))|((?<= )[^ ]*$)");
		List<String> result = new ArrayList<>();
		String token = null;
		while ((token = scanner.findInLine(pattern)) != null) {
			if ((token.startsWith("\'") && token.endsWith("\'")) || (token.startsWith("\"") && token.endsWith("\""))) {
				token = token.substring(1, token.length() - 1);
			}
			token = token.replaceAll("\'", "'").replaceAll("\"", "\"");
			result.add(token);
		}
		scanner.close();
		return result.toArray(new String[result.size()]);
	}

}
