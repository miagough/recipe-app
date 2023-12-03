package utils

import utils.ScannerInput.readNextInt
import java.util.Scanner

object ValidateInput {

    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    @JvmStatic
    fun readValidRating(prompt: String?): Int {
        var input = readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1, 5))
                return input
            else {
                print("Invalid rating $input.")
                input = readNextInt(prompt)
            }
        } while (true)
    }
}