public class KMP {
    // Function to pre-process the pattern string and create lookup table
    public static int[] computePrefixTable(String pattern) {
        int n = pattern.length();
        int[] prefixTable = new int[n];
        int j = 0;
        for (int i = 1; i < n; i++) {
            // If the current character in pattern matches with the character at position 'j' in prefix,
            // increment both 'i' and 'j'
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = prefixTable[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            prefixTable[i] = j;
        }
        return prefixTable;
    }

    // Function to perform KMP search
    public static int kmpSearch(String text, String pattern) {
        int[] prefixTable = computePrefixTable(pattern);
        int m = pattern.length();
        int n = text.length();
        int i = 0; // index in the text string
        int j = 0; // index in the pattern string
        while (i < n) {
            if (j == m) {
                return i - j + 1;
            }
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
            } else {
                if (j != 0)
                    j = prefixTable[j - 1];
                else
                    i++;
            }
        }
        return -1; // pattern not found in the text
    }

    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABAB";
        int index = kmpSearch(text, pattern);
        if (index != -1)
            System.out.println("Pattern found at index " + index);
        else
            System.out.println("Pattern not found in the text");
    }
}