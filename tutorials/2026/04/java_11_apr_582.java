public class MergeSort {
    public static void main(String[] args) {
        int[] array = {5, 2, 8, 3, 1, 4, 6};
        System.out.println("Original Array: ");
        printArray(array);

        int[] sortedArray = mergeSort(array);
        System.out.println("Sorted Array: ");
        printArray(sortedArray);
    }

    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        }
        
        // Find the middle point of the array
        int mid = array.length / 2;
        
        // Divide the array into two halves
        int[] leftHalf = new int[mid];
        System.arraycopy(array, 0, leftHalf, 0, mid);
        int[] rightHalf = new int[array.length - mid];
        System.arraycopy(array, mid, rightHalf, 0, array.length - mid);

        // Recursively sort the two halves
        leftHalf = mergeSort(leftHalf);
        rightHalf = mergeSort(rightHalf);

        // Merge the sorted halves into a single sorted array
        return merge(leftHalf, rightHalf);
    }

    public static int[] merge(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;

        // Compare elements from both arrays and place the smaller one in the merged array
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                merged[k++] = left[i++];
            } else {
                merged[k++] = right[j++];
            }
        }

        // Place any remaining elements from both arrays
        while (i < left.length) {
            merged[k++] = left[i++];
        }

        while (j < right.length) {
            merged[k++] = right[j++];
        }

        return merged;
    }

    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}