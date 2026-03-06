import java.util.Arrays;

public class BinarySearch {

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        binarySearch.search();
    }

    public void search() {
        int[] array = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println("Original array: " + Arrays.toString(array));
        System.out.println("Target element: 23");
        int result = binarySearch(array, 23);
        if (result == -1) {
            System.out.println("Element not found in array.");
        } else {
            System.out.println("Element found at index: " + result);
        }
    }

    public int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            System.out.println("Current array: " + Arrays.toString(array));
            System.out.println("Mid index: " + mid);
            System.out.println("Target element at mid index: " + array[mid]);
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}