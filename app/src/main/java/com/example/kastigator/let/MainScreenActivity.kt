package com.example.kastigator.let

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.*
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.util.*

class MainScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        //Setting variables and constants with widgets START
        var newStocks = findViewById(R.id.stocks_Enter) as EditText
        var accountUser = findViewById(R.id.account_Name) as EditText
        var currencyTypeUser = findViewById(R.id.currency_Type) as EditText
        var descriptionUser = findViewById(R.id.description_Panel) as EditText
        var currentBalance = findViewById(R.id.balance_View) as TextView
        var accountRecords = findViewById(R.id.record_List) as TextView
        val withdrawButton = findViewById(R.id.withdraw_Button) as Button
        val depositButton = findViewById(R.id.deposit_Button) as Button
        var deleteCheck = findViewById(R.id.accountDelete) as CheckBox
        val deleteButton = findViewById(R.id.accountDelete_button) as Button
        var counterScreenCurrent = findViewById(R.id.counter_current) as TextView
        var counterScreenFinal = findViewById(R.id.counter_final) as TextView
        val nextAccountButton = findViewById(R.id.accountNEX) as Button
        val previousAccountButton = findViewById(R.id.accountsPRE) as Button
        //Setting variables and constants with widgets END


        //reformatting the takken date START
        var myDate = ""
        val tekkenDate = Date().toString() //takes date from device and converts to string
        myDate = "[ " + tekkenDate.substring(8 , 10) + "/" + tekkenDate.substring(4 , 7) + "/" + tekkenDate.substring(32 , 34) + " ]"
        //reformatting the takken date END



        //***************************Counter Reading setting START********************************
        var accountsCounter = 1
        var newFileName = "LET_AccountDetails.txt"
        var C_loopEnd = false
        while (C_loopEnd != true) {
            val filePathCR = getFilesDir() //takes files directory for the app
            val newFilePathCR = File(filePathCR, "/LET_Account") //adding your own folder
            var successCR = true
            if (!newFilePathCR.exists()) { //if does not exist
                //Counter is not affected as it's folder directory
                C_loopEnd = true
            }
            if (successCR) {
                val accountsFile = File(newFilePathCR, newFileName)

                if (!accountsFile.exists()) { //if does not exist
                    //Counter decreases by one
                    accountsCounter = accountsCounter - 1
                    C_loopEnd = true

                } else {
                    if (successCR) {
                        // directory exists or already created
                        //Increment Counter
                        accountsCounter = accountsCounter + 1

                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file(s).", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (C_loopEnd != true) {
                val textCounter = accountsCounter.toString()
                newFileName = "LET_AccountDetails" + textCounter + ".txt"
            }
        }
        if (accountsCounter > 1) {
            counterScreenCurrent.setText("1")
            counterScreenFinal.setText(accountsCounter.toString())

        }
        //*************************Counter Reading setting END**********************************



        //File RECORDS READING usage START*****
        var newRecord = ""
        val filePathRR = getFilesDir() //takes files directory for the app
        val newFilePathRR = File(filePathRR , "/LET_Records") //adding your own folder
        var successRR = true
        if (!newFilePathRR.exists()) { //if does not exist
            //create directory but for now do nothing
            accountRecords.setText("No records found yet.")
        }
        if (successRR) {
            var newFile = ""
            val FileCheck = counterScreenCurrent.getText().toString().toInt()
            if (FileCheck == 1) {
                newFile = "LET_RecordDetails.txt"
            } else {
                val textFileCheck = FileCheck.toString()
                newFile = "LET_RecordDetails" + textFileCheck + ".txt"

            }
            val recordsFile = File(newFilePathRR, newFile)

            if (!recordsFile.exists()) { //if does not exist
                //create and write on to it but for now do nothing
                accountRecords.setText("No records found yet.")
                Toast.makeText(this, "Records File does not exist.", Toast.LENGTH_SHORT).show()

            } else {
                if (successRR) {
                    // directory exists or already created
                    //read from it
                    newRecord = recordsFile.readText() //reads from file
                    accountRecords.setText(newRecord) //displays on screen
                    Toast.makeText(this, "Records File exist.", Toast.LENGTH_SHORT).show()


                } else {
                    // directory creation is not successful
                    Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //File RECORDS READING usage END*****



        //File RECORDS WRITING usage START*****
        val filePathRW = getFilesDir() //takes files directory for the app
        val newFilePathRW = File(filePathRW , "/LET_Records") //adding your own folder
        var successRW = true
        if (!newFilePathRW.exists()) { //if does not exist
            newFilePathRW.mkdirs() //create directory
        }
        //File RECORDS WRITING usage END*****



        //File ACCOUNT READING usage START*****
        val filePathAR = getFilesDir() //takes files directory for the app
        val newFilePathAR = File(filePathAR , "/LET_Account") //adding your own folder
        var successAR = true
        if (!newFilePathAR.exists()) { //if does not exist
            //create directory but for now do nothing
            currentBalance.setText("00.00")
        }
        if (successAR) {
            var newFile = ""
            val FileCheck = counterScreenCurrent.getText().toString().toInt()
            if (FileCheck == 1) {
                newFile = "LET_AccountDetails.txt"
            } else {
                val textFileCheck = FileCheck.toString()
                newFile = "LET_AccountDetails" + textFileCheck + ".txt"

            }
            val accountFile = File(newFilePathAR, newFile)

            if (!accountFile.exists()) { //if does not exist
                //create and write on to it but for now do nothing
                currentBalance.setText("00.00")
                Toast.makeText(this, "Account File does not exist.", Toast.LENGTH_SHORT).show()

            } else {
                if (successAR) {
                    // directory exists or already created
                    //read from it

                    //Taking details from accounts file START

                    var counterFile = 0
                    var counterLoopEnd = 0
                    val readDetails = accountFile.readLines().toString()

                    var accountTekkenName = ""
                    var currencyTekkenType = ""
                    var amountTekken = ""

                    var smallerLoopEnd = false
                    var loopStart = false


                    while (counterLoopEnd < 3) {
                        if (counterLoopEnd == 0) {
                            while (smallerLoopEnd != true) {
                                if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                    loopStart = false
                                    counterLoopEnd = counterLoopEnd + 1
                                    smallerLoopEnd = true
                                }
                                if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                    loopStart = true
                                    counterFile = counterFile + 1
                                }
                                if (loopStart == true) {
                                    accountTekkenName = accountTekkenName + readDetails.substring(counterFile , counterFile + 1)
                                }
                                counterFile = counterFile + 1
                            }
                        }
                        smallerLoopEnd = false

                        if (counterLoopEnd == 1) {
                            while (smallerLoopEnd != true) {
                                if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                    loopStart = false
                                    counterLoopEnd = counterLoopEnd + 1
                                    smallerLoopEnd = true
                                }
                                if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                    loopStart = true
                                    counterFile = counterFile + 1
                                }
                                if (loopStart == true) {
                                    currencyTekkenType = currencyTekkenType + readDetails.substring(counterFile , counterFile + 1)
                                }
                                counterFile = counterFile + 1
                            }

                        }

                        smallerLoopEnd = false

                        if (counterLoopEnd == 2) {
                            while (smallerLoopEnd != true) {
                                if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                    loopStart = false
                                    counterLoopEnd = counterLoopEnd + 1
                                    smallerLoopEnd = true
                                }
                                if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                    loopStart = true
                                    counterFile = counterFile + 1
                                }
                                if (loopStart == true) {
                                    amountTekken = amountTekken + readDetails.substring(counterFile , counterFile + 1)
                                }
                                counterFile = counterFile + 1
                            }
                        }
                    }

                    accountUser.setText(accountTekkenName)
                    currencyTypeUser.setText(currencyTekkenType)
                    currentBalance.setText(amountTekken)
                    //Taking details from accounts file END

                    Toast.makeText(this, "Account File exist", Toast.LENGTH_SHORT).show()


                } else {
                    // directory creation is not successful
                    Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //File ACCOUNT READING usage END*****

        //File ACCOUNT WRITING usage START*****

        val filePathAW = getFilesDir() //takes files directory for the app
        val newFilePathAW = File(filePathAW , "/LET_Account") //adding your own folder
        var successAW = true

        if (!newFilePathAW.exists()) { //if does not exist
            newFilePathAW.mkdirs() //create directory
        }

        //File ACCOUNT WRITING usage END*****

        //activity below on clicking deposit button START
        depositButton.setOnClickListener {

            //Limit checks are here START
            var checkProceed = false
            var checkOne = false
            var checkTwo = false
            var descriptionText = descriptionUser.getText().toString()
            var descriptionLength = descriptionText.length
            if (descriptionLength > 20) {
                Toast.makeText(this, "Description too long!", Toast.LENGTH_SHORT).show()
            } else {
                checkOne = true
            }
            var currencyTypeText = currencyTypeUser.getText().toString()
            var currencyTypelength = currencyTypeText.length
            if (currencyTypelength > 3) {
                Toast.makeText(this, "Currency Type too long!", Toast.LENGTH_SHORT).show()
            } else {
                checkTwo = true
            }
            if (checkOne == true) {
                if (checkTwo == true) {
                checkProceed = true
                }
            }

            //Limit checks are here END


            if (checkProceed == true) {

                var newAmount = currentBalance.getText().toString().toFloat()
                var stocksInput = newStocks.getText().toString().toFloat()
                newAmount = newAmount + stocksInput
                var newAmountSTR = newAmount.toString()
                var accountName = accountUser.getText().toString()
                var currencyType = currencyTypeUser.getText().toString()
                var fullDetails = "ACN:$accountName?CRC:$currencyType?AMT:$newAmountSTR??"

                //Account WRITING START
                if (successAW) {

                    var newFile = ""
                    val FileCheck = counterScreenCurrent.getText().toString().toInt()
                    if (FileCheck == 1) {
                        newFile = "LET_AccountDetails.txt"
                    } else {
                        val textFileCheck = FileCheck.toString()
                        newFile = "LET_AccountDetails" + textFileCheck + ".txt"
                    }

                    var accountsFile = File(newFilePathAW, newFile)


                    if (!accountsFile.exists()) { //if does not exist
                        //create and write on to it
                        accountsFile.writeText(fullDetails)

                    } else {
                        if (successAW) {
                            // directory exists or already created
                            //write on to it
                            accountsFile.writeText(fullDetails)

                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while creating file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //Account WRITING END

                //File RECORDS WRITING usage START*****
                if (successRW) {
                    var newFile = ""
                    val FileCheck = counterScreenCurrent.getText().toString().toInt()
                    if (FileCheck == 1) {
                        newFile = "LET_RecordDetails.txt"
                    } else {
                        val textFileCheck = FileCheck.toString()
                        newFile = "LET_RecordDetails" + textFileCheck + ".txt"

                    }

                    val recordsFile = File(newFilePathRW, newFile)

                    if (!recordsFile.exists()) { //if does not exist
                        //create and write on to it

                        var itemDescription = descriptionUser.getText().toString()
                        if (itemDescription == "(Optional)") {
                            itemDescription = "No Info"
                        }
                        var currencyT = currencyTypeUser.getText().toString()
                        var amountT = newStocks.getText().toString()
                        var newRecord = " $itemDescription $myDate                    $currencyT. +$amountT. "
                        recordsFile.writeText(newRecord + "\n")
                    } else {
                        if (successRW) {
                            // directory exists or already created
                            //write on to it
                            var itemDescription = descriptionUser.getText().toString()
                            if (itemDescription == "(Optional)") {
                                itemDescription = "No Info"
                            }
                            var currencyT = currencyTypeUser.getText().toString()
                            var amountT = newStocks.getText().toString()
                            var newRecord = " $itemDescription $myDate                    $currencyT. +$amountT. "
                            var oldRecords = recordsFile.readText()
                            recordsFile.writeText(newRecord + "\n")
                            recordsFile.appendText(oldRecords)

                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while creating file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //File RECORDS WRITING usage END*****

            }

            //File RECORDS READING usage START*****
            var newRecordS = ""
            val filePathRRS = getFilesDir() //takes files directory for the app
            val newFilePathRRS = File(filePathRRS , "/LET_Records") //adding your own folder
            var successRRS = true
            if (!newFilePathRRS.exists()) { //if does not exist
                //create directory but for now do nothing
            }
            if (successRRS) {
                var newFile = ""
                val FileCheck = counterScreenCurrent.getText().toString().toInt()
                if (FileCheck == 1) {
                    newFile = "LET_RecordDetails.txt"
                } else {
                    val textFileCheck = FileCheck.toString()
                    newFile = "LET_RecordDetails" + textFileCheck + ".txt"
                }

                val recordsFile = File(newFilePathRRS, newFile)

                if (!recordsFile.exists()) { //if does not exist
                    //create and write on to it but for now do nothing

                } else {
                    if (successRRS) {
                        // directory exists or already created
                        //read from it
                        newRecordS = recordsFile.readText()
                        accountRecords.setText(newRecordS)


                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //File RECORDS READING usage END*****

            //File ACCOUNT READING usage START*****
            val filePathAR = getFilesDir() //takes files directory for the app
            val newFilePathAR = File(filePathAR , "/LET_Account") //adding your own folder
            var successAR = true
            if (!newFilePathAR.exists()) { //if does not exist
                //create directory but for now do nothing
            }
            if (successAR) {
                var newFile = ""
                val FileCheck = counterScreenCurrent.getText().toString().toInt()
                if (FileCheck == 1) {
                    newFile = "LET_AccountDetails.txt"
                } else {
                    val textFileCheck = FileCheck.toString()
                    newFile = "LET_AccountDetails" + textFileCheck + ".txt"
                }
                val accountFile = File(newFilePathAR, newFile)

                if (!accountFile.exists()) { //if does not exist
                    //create and write on to it but for now do nothing

                } else {
                    if (successAR) {
                        // directory exists or already created
                        //read from it

                        //Taking details from accounts file START

                        var counterFile = 0
                        var counterLoopEnd = 0
                        val readDetails = accountFile.readLines().toString()

                        var accountTekkenName = ""
                        var currencyTekkenType = ""
                        var amountTekken = ""

                        var smallerLoopEnd = false
                        var loopStart = false


                        while (counterLoopEnd < 3) {
                            if (counterLoopEnd == 0) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        accountTekkenName = accountTekkenName + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }
                            }
                            smallerLoopEnd = false

                            if (counterLoopEnd == 1) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        currencyTekkenType = currencyTekkenType + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }

                            }

                            smallerLoopEnd = false

                            if (counterLoopEnd == 2) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        amountTekken = amountTekken + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }
                            }
                        }

                        accountUser.setText(accountTekkenName)
                        currencyTypeUser.setText(currencyTekkenType)
                        currentBalance.setText(amountTekken)
                        //Taking details from accounts file END
                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //File ACCOUNT READING usage END*****


        }
        //activity on clicking deposit button END

        //activity below on clicking withdraw button START
        withdrawButton.setOnClickListener {

            //Limit checks are here START
            var checkProceed = false
            var checkOne = false
            var checkTwo = false
            var descriptionText = descriptionUser.getText().toString()
            var descriptionLength = descriptionText.length
            if (descriptionLength > 20) {
                Toast.makeText(this, "Description too long!", Toast.LENGTH_SHORT).show()
            } else {
                checkOne = true
            }
            var currencyTypeText = currencyTypeUser.getText().toString()
            var currencyTypelength = currencyTypeText.length
            if (currencyTypelength > 3) {
                Toast.makeText(this, "Currency Type too long!", Toast.LENGTH_SHORT).show()
            } else {
                checkTwo = true
            }
            if (checkOne == true) {
                if (checkTwo == true) {
                    checkProceed = true
                }
            }

            //Limit checks are here END



            if (checkProceed == true) {

                var newAmount = currentBalance.getText().toString().toFloat()
                var stocksInput = newStocks.getText().toString().toFloat()
                newAmount = newAmount - stocksInput
                var newAmountSTR = newAmount.toString()
                var accountName = accountUser.getText().toString()
                var currencyType = currencyTypeUser.getText().toString()
                var fullDetails = "ACN:$accountName?CRC:$currencyType?AMT:$newAmountSTR??"

                //Account WRITING START
                if (successAW) {
                    var newFile = ""
                    val FileCheck = counterScreenCurrent.getText().toString().toInt()
                    if (FileCheck == 1) {
                        newFile = "LET_AccountDetails.txt"
                    } else {
                        val textFileCheck = FileCheck.toString()
                        newFile = "LET_AccountDetails" + textFileCheck + ".txt"
                    }
                    var accountsFile = File(newFilePathAW, newFile)


                    if (!accountsFile.exists()) { //if does not exist
                        //create and write on to it
                        accountsFile.writeText(fullDetails)

                    } else {
                        if (successAW) {
                            // directory exists or already created
                            //write on to it
                            accountsFile.writeText(fullDetails)

                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while creating file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //Account WRITING END

                //File RECORDS WRITING usage START*****
                if (successRW) {
                    var newFile = ""
                    val FileCheck = counterScreenCurrent.getText().toString().toInt()
                    if (FileCheck == 1) {
                        newFile = "LET_RecordDetails.txt"
                    } else {
                        val textFileCheck = FileCheck.toString()
                        newFile = "LET_RecordDetails" + textFileCheck + ".txt"
                    }
                    val recordsFile = File(newFilePathRW, newFile)
                    val myCharacter = "-"

                    if (!recordsFile.exists()) { //if does not exist
                        //create and write on to it

                        var itemDescription = descriptionUser.getText().toString()
                        if (itemDescription == "(Optional)") {
                            itemDescription = "No Info"
                        }
                        var currencyT = currencyTypeUser.getText().toString()
                        var amountT = newStocks.getText().toString()
                        var newRecord = " $itemDescription $myDate                    $currencyT. -$amountT. "
                        recordsFile.writeText(newRecord + "\n")
                    } else {
                        if (successRW) {
                            // directory exists or already created
                            //write on to it
                            var itemDescription = descriptionUser.getText().toString()
                            if (itemDescription == "(Optional)") {
                                itemDescription = "No Info"
                            }
                            var currencyT = currencyTypeUser.getText().toString()
                            var amountT = newStocks.getText().toString()
                            var newRecord = " $itemDescription $myDate                    $currencyT. -$amountT. "
                            var oldRecords = recordsFile.readText()
                            recordsFile.writeText(newRecord + "\n")
                            recordsFile.appendText(oldRecords)

                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while creating file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //File RECORDS WRITING usage END*****

            }

            //File RECORDS READING usage START*****
            var newRecordS = ""
            val filePathRRS = getFilesDir() //takes files directory for the app
            val newFilePathRRS = File(filePathRRS , "/LET_Records") //adding your own folder
            var successRRS = true
            if (!newFilePathRRS.exists()) { //if does not exist
                //create directory but for now do nothing
            }
            if (successRRS) {
                var newFile = ""
                val FileCheck = counterScreenCurrent.getText().toString().toInt()
                if (FileCheck == 1) {
                    newFile = "LET_RecordDetails.txt"
                } else {
                    val textFileCheck = FileCheck.toString()
                    newFile = "LET_RecordDetails" + textFileCheck + ".txt"
                }
                val recordsFile = File(newFilePathRRS, newFile)

                if (!recordsFile.exists()) { //if does not exist
                    //create and write on to it but for now do nothing

                } else {
                    if (successRRS) {
                        // directory exists or already created
                        //read from it
                        newRecordS = recordsFile.readText()
                        accountRecords.setText(newRecordS)


                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //File RECORDS READING usage END*****

            //File ACCOUNT READING usage START*****
            val filePathAR = getFilesDir() //takes files directory for the app
            val newFilePathAR = File(filePathAR , "/LET_Account") //adding your own folder
            var successAR = true
            if (!newFilePathAR.exists()) { //if does not exist
                //create directory but for now do nothing
            }
            if (successAR) {
                var newFile = ""
                val FileCheck = counterScreenCurrent.getText().toString().toInt()
                if (FileCheck == 1) {
                    newFile = "LET_AccountDetails.txt"
                } else {
                    val textFileCheck = FileCheck.toString()
                    newFile = "LET_AccountDetails" + textFileCheck + ".txt"
                }
                val accountFile = File(newFilePathAR, newFile)

                if (!accountFile.exists()) { //if does not exist
                    //create and write on to it but for now do nothing

                } else {
                    if (successAR) {
                        // directory exists or already created
                        //read from it

                        //Taking details from accounts file START

                        var counterFile = 0
                        var counterLoopEnd = 0
                        val readDetails = accountFile.readLines().toString()

                        var accountTekkenName = ""
                        var currencyTekkenType = ""
                        var amountTekken = ""

                        var smallerLoopEnd = false
                        var loopStart = false


                        while (counterLoopEnd < 3) {
                            if (counterLoopEnd == 0) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        accountTekkenName = accountTekkenName + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }
                            }
                            smallerLoopEnd = false

                            if (counterLoopEnd == 1) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        currencyTekkenType = currencyTekkenType + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }

                            }

                            smallerLoopEnd = false

                            if (counterLoopEnd == 2) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        amountTekken = amountTekken + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }
                            }
                        }

                        accountUser.setText(accountTekkenName)
                        currencyTypeUser.setText(currencyTekkenType)
                        currentBalance.setText(amountTekken)
                        //Taking details from accounts file END
                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //File ACCOUNT READING usage END*****


        }
        //activity on clicking withdraw button END


        //activity below on changing to new account START
        nextAccountButton.setOnClickListener {

            var currentCounter = counterScreenCurrent.getText().toString().toInt()
            var finalCounter = counterScreenFinal.getText().toString().toInt()

            if (currentCounter == finalCounter) { //if equal.
                // create new file.
                val NewCounter = finalCounter + 1
                counterScreenCurrent.setText(NewCounter.toString())
                counterScreenFinal.setText(NewCounter.toString())
                Toast.makeText(this, "New Account will be created!", Toast.LENGTH_SHORT).show()

                //changing UI to defaults
                accountUser.setText("")
                currencyTypeUser.setText("")
                currentBalance.setText("0.0")
                deleteCheck.isChecked = false
                newStocks.setText("")
                descriptionUser.setText("(Optional)")
                accountRecords.setText("No Records Yet...")
                //changing UI to defaults

            } else {
                var newCounter = currentCounter + 1
                counterScreenCurrent.setText(newCounter.toString())
                currentCounter = counterScreenCurrent.getText().toString().toInt()
                //File ACCOUNT READING usage START*****
                val filePathAR = getFilesDir() //takes files directory for the app
                val newFilePathAR = File(filePathAR , "/LET_Account") //adding your own folder
                var successAR = true
                if (!newFilePathAR.exists()) { //if does not exist
                    //create directory but for now do nothing
                    currentBalance.setText("00.00")
                }
                if (successAR) {
                    val textcurrentCounter = currentCounter.toString()
                    val newAccount = "LET_AccountDetails" + textcurrentCounter + ".txt"
                    val accountFile = File(newFilePathAR, newAccount)

                    if (!accountFile.exists()) { //if does not exist
                        //create and write on to it but for now do nothing
                        currentBalance.setText("00.00")
                        Toast.makeText(this, "Account File does not exist.", Toast.LENGTH_SHORT).show()

                    } else {
                        if (successAR) {
                            // directory exists or already created
                            //read from it

                            //Taking details from accounts file START

                            var counterFile = 0
                            var counterLoopEnd = 0
                            val readDetails = accountFile.readLines().toString()

                            var accountTekkenName = ""
                            var currencyTekkenType = ""
                            var amountTekken = ""

                            var smallerLoopEnd = false
                            var loopStart = false


                            while (counterLoopEnd < 3) {
                                if (counterLoopEnd == 0) {
                                    while (smallerLoopEnd != true) {
                                        if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                            loopStart = false
                                            counterLoopEnd = counterLoopEnd + 1
                                            smallerLoopEnd = true
                                        }
                                        if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                            loopStart = true
                                            counterFile = counterFile + 1
                                        }
                                        if (loopStart == true) {
                                            accountTekkenName = accountTekkenName + readDetails.substring(counterFile , counterFile + 1)
                                        }
                                        counterFile = counterFile + 1
                                    }
                                }
                                smallerLoopEnd = false

                                if (counterLoopEnd == 1) {
                                    while (smallerLoopEnd != true) {
                                        if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                            loopStart = false
                                            counterLoopEnd = counterLoopEnd + 1
                                            smallerLoopEnd = true
                                        }
                                        if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                            loopStart = true
                                            counterFile = counterFile + 1
                                        }
                                        if (loopStart == true) {
                                            currencyTekkenType = currencyTekkenType + readDetails.substring(counterFile , counterFile + 1)
                                        }
                                        counterFile = counterFile + 1
                                    }

                                }

                                smallerLoopEnd = false

                                if (counterLoopEnd == 2) {
                                    while (smallerLoopEnd != true) {
                                        if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                            loopStart = false
                                            counterLoopEnd = counterLoopEnd + 1
                                            smallerLoopEnd = true
                                        }
                                        if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                            loopStart = true
                                            counterFile = counterFile + 1
                                        }
                                        if (loopStart == true) {
                                            amountTekken = amountTekken + readDetails.substring(counterFile , counterFile + 1)
                                        }
                                        counterFile = counterFile + 1
                                    }
                                }
                            }

                            accountUser.setText(accountTekkenName)
                            currencyTypeUser.setText(currencyTekkenType)
                            currentBalance.setText(amountTekken)
                            //Taking details from accounts file END

                            //Toast.makeText(this, "Account File exist", Toast.LENGTH_SHORT).show()


                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //File ACCOUNT READING usage END*****

                //File RECORDS READING usage START*****
                var newRecord = ""
                val filePathRR = getFilesDir() //takes files directory for the app
                val newFilePathRR = File(filePathRR , "/LET_Records") //adding your own folder
                var successRR = true
                if (!newFilePathRR.exists()) { //if does not exist
                    //create directory but for now do nothing
                    accountRecords.setText("No records found yet.")
                }
                if (successRR) {
                    val textcurrentCounter = currentCounter.toString()
                    val newFile = "LET_RecordDetails" + textcurrentCounter + ".txt"
                    val recordsFile = File(newFilePathRR, newFile)

                    if (!recordsFile.exists()) { //if does not exist
                        //create and write on to it but for now do nothing
                        accountRecords.setText("No records found yet.")
                        Toast.makeText(this, "Records File does not exist.", Toast.LENGTH_SHORT).show()

                    } else {
                        if (successRR) {
                            // directory exists or already created
                            //read from it
                            newRecord = recordsFile.readText() //reads from file
                            accountRecords.setText(newRecord) //displays on screen
                            //Toast.makeText(this, "Records File exist.", Toast.LENGTH_SHORT).show()


                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //File RECORDS READING usage END*****


            }

        }
        //activity on changing to new account END


        //activity below on changing to previous account START
        previousAccountButton.setOnClickListener {

            var currentCounter = counterScreenCurrent.getText().toString().toInt()
            var finalCounter = counterScreenFinal.getText().toString().toInt()

            if (currentCounter == 1) { //if at the start.
                Toast.makeText(this, "No more previous accounts.", Toast.LENGTH_SHORT).show()

            } else {
                var newCounter = currentCounter - 1
                counterScreenCurrent.setText(newCounter.toString())
                currentCounter = counterScreenCurrent.getText().toString().toInt()
                //File ACCOUNT READING usage START*****
                val filePathAR = getFilesDir() //takes files directory for the app
                val newFilePathAR = File(filePathAR , "/LET_Account") //adding your own folder
                var successAR = true
                if (!newFilePathAR.exists()) { //if does not exist
                    //create directory but for now do nothing
                    currentBalance.setText("00.00")
                }
                if (successAR) {
                    var newAccount = ""

                    if (currentCounter == 1) {
                        newAccount = "LET_AccountDetails.txt"
                    } else {
                        val textcurrentCounter = currentCounter.toString()
                        newAccount = "LET_AccountDetails" + textcurrentCounter + ".txt"
                    }
                    val accountFile = File(newFilePathAR, newAccount)
                    if (!accountFile.exists()) { //if does not exist
                        //create and write on to it but for now do nothing
                        currentBalance.setText("00.00")
                        Toast.makeText(this, "Account File does not exist.", Toast.LENGTH_SHORT).show()

                    } else {
                        if (successAR) {
                            // directory exists or already created
                            //read from it

                            //Taking details from accounts file START

                            var counterFile = 0
                            var counterLoopEnd = 0
                            val readDetails = accountFile.readLines().toString()

                            var accountTekkenName = ""
                            var currencyTekkenType = ""
                            var amountTekken = ""

                            var smallerLoopEnd = false
                            var loopStart = false


                            while (counterLoopEnd < 3) {
                                if (counterLoopEnd == 0) {
                                    while (smallerLoopEnd != true) {
                                        if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                            loopStart = false
                                            counterLoopEnd = counterLoopEnd + 1
                                            smallerLoopEnd = true
                                        }
                                        if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                            loopStart = true
                                            counterFile = counterFile + 1
                                        }
                                        if (loopStart == true) {
                                            accountTekkenName = accountTekkenName + readDetails.substring(counterFile , counterFile + 1)
                                        }
                                        counterFile = counterFile + 1
                                    }
                                }
                                smallerLoopEnd = false

                                if (counterLoopEnd == 1) {
                                    while (smallerLoopEnd != true) {
                                        if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                            loopStart = false
                                            counterLoopEnd = counterLoopEnd + 1
                                            smallerLoopEnd = true
                                        }
                                        if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                            loopStart = true
                                            counterFile = counterFile + 1
                                        }
                                        if (loopStart == true) {
                                            currencyTekkenType = currencyTekkenType + readDetails.substring(counterFile , counterFile + 1)
                                        }
                                        counterFile = counterFile + 1
                                    }

                                }

                                smallerLoopEnd = false

                                if (counterLoopEnd == 2) {
                                    while (smallerLoopEnd != true) {
                                        if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                            loopStart = false
                                            counterLoopEnd = counterLoopEnd + 1
                                            smallerLoopEnd = true
                                        }
                                        if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                            loopStart = true
                                            counterFile = counterFile + 1
                                        }
                                        if (loopStart == true) {
                                            amountTekken = amountTekken + readDetails.substring(counterFile , counterFile + 1)
                                        }
                                        counterFile = counterFile + 1
                                    }
                                }
                            }

                            accountUser.setText(accountTekkenName)
                            currencyTypeUser.setText(currencyTekkenType)
                            currentBalance.setText(amountTekken)
                            //Taking details from accounts file END

                            //Toast.makeText(this, "Account File exist", Toast.LENGTH_SHORT).show()


                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //File ACCOUNT READING usage END*****

                //File RECORDS READING usage START*****
                var newRecord = ""
                val filePathRR = getFilesDir() //takes files directory for the app
                val newFilePathRR = File(filePathRR , "/LET_Records") //adding your own folder
                var successRR = true
                if (!newFilePathRR.exists()) { //if does not exist
                    //create directory but for now do nothing
                    accountRecords.setText("No records found yet.")
                }
                if (successRR) {

                    var newFile = ""

                    if (currentCounter == 1) {
                        newFile = "LET_RecordDetails.txt"
                    } else {
                        val textcurrentCounter = currentCounter.toString()
                        newFile = "LET_RecordDetails" + textcurrentCounter + ".txt"
                    }

                    val recordsFile = File(newFilePathRR, newFile)

                    if (!recordsFile.exists()) { //if does not exist
                        //create and write on to it but for now do nothing
                        accountRecords.setText("No records found yet.")
                        Toast.makeText(this, "Records File does not exist.", Toast.LENGTH_SHORT).show()

                    } else {
                        if (successRR) {
                            // directory exists or already created
                            //read from it
                            newRecord = recordsFile.readText() //reads from file
                            accountRecords.setText(newRecord) //displays on screen
                            //Toast.makeText(this, "Records File exist.", Toast.LENGTH_SHORT).show()


                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //File RECORDS READING usage END*****


            }

        }
        //activity on changing to previous account END

        //activity below on deleting accounts START
        deleteButton.setOnClickListener {
            //deletion check START
            var FileExistsAccount = false
            var FileExistsRecord = false
            if (deleteCheck.isChecked == false) {
                Toast.makeText(this, "Confirm file deletion.", Toast.LENGTH_SHORT).show()
            } else { //deletion check END

                //deleting files START***

                //deleting account file START
                val FileToDeleteCounter = counterScreenCurrent.getText().toString().toInt()
                val filePathAccount = getFilesDir()
                val newFilePathAccount = File(filePathAccount, "/LET_Account")
                var FileToDeleteName = ""
                if (FileToDeleteCounter == 1) {
                    FileToDeleteName = "LET_AccountDetails.txt"
                } else {
                    FileToDeleteName = "LET_AccountDetails" + FileToDeleteCounter.toString() + ".txt"
                }
                val FileToDelete = File(newFilePathAccount, FileToDeleteName)
                if (!FileToDelete.exists()) {
                    Toast.makeText(this, "Account File does not exist.", Toast.LENGTH_SHORT).show()
                } else {
                    FileToDelete.delete()
                    FileExistsAccount = true
                }
                //deleting account file END

                //deleting record file START
                val FileToDeleteCounterR = counterScreenCurrent.getText().toString().toInt()
                val filePathRecord = getFilesDir()
                val newFilePathRecord = File(filePathRecord, "/LET_Records")
                var FileToDeleteNameR = ""
                if (FileToDeleteCounterR == 1) {
                    FileToDeleteNameR = "LET_RecordDetails.txt"
                } else {
                    FileToDeleteNameR = "LET_RecordDetails" + FileToDeleteCounterR.toString() + ".txt"
                }
                val FileToDeleteR = File(newFilePathRecord, FileToDeleteNameR)
                if (!FileToDeleteR.exists()) {
                    Toast.makeText(this, "Record File does not exist.", Toast.LENGTH_SHORT).show()
                } else {
                    FileToDeleteR.delete()
                    FileExistsRecord = true
                }
                //deleting record file END


                //deleting files END***


                //renaming files START***

                //renaming account file START
                if (FileExistsAccount == true) {
                    if (FileExistsRecord == true) {
                        var FileDeletedAccount = counterScreenCurrent.getText().toString().toInt()
                        var RemainingFilesToRename = counterScreenFinal.getText().toString().toInt() - counterScreenCurrent.getText().toString().toInt()
                        var NextFileToRenameCounter = FileDeletedAccount + 1
                        var FileNewNameCounter = FileDeletedAccount
                        var NewName = ""
                        var PreviousName = ""
                        while (RemainingFilesToRename > 0) {
                            if (FileDeletedAccount == 1) {
                                PreviousName = "LET_AccountDetails" + NextFileToRenameCounter.toString() + ".txt"
                                NewName = "LET_AccountDetails.txt"
                                FileDeletedAccount = 0 //Must to proceed
                            } else {
                                PreviousName = "LET_AccountDetails" + NextFileToRenameCounter.toString() + ".txt"
                                NewName = "LET_AccountDetails" + FileNewNameCounter.toString() + ".txt"
                            }
                            val filePathAccount = getFilesDir()
                            val newFilePathAccount = File(filePathAccount, "/LET_Account")
                            val OldFileAccount = File(newFilePathAccount, PreviousName)
                            val NewFileAccount = File(newFilePathAccount, NewName)
                            OldFileAccount.renameTo(NewFileAccount)
                            RemainingFilesToRename = RemainingFilesToRename - 1
                            NextFileToRenameCounter = NextFileToRenameCounter + 1
                            FileNewNameCounter = FileNewNameCounter + 1
                        }
                        //renaming account file END

                        //renaming record file START
                        var FileDeletedRecord = counterScreenCurrent.getText().toString().toInt()
                        var RemainingFilesToRenameR = counterScreenFinal.getText().toString().toInt() - counterScreenCurrent.getText().toString().toInt()
                        var NextFileToRenameCounterR = FileDeletedRecord + 1
                        var FileNewNameCounterR = FileDeletedRecord
                        var NewNameR = ""
                        var PreviousNameR = ""
                        while (RemainingFilesToRenameR > 0) {
                            if (FileDeletedRecord == 1) {
                                PreviousNameR = "LET_RecordDetails" + NextFileToRenameCounterR.toString() + ".txt"
                                NewNameR = "LET_RecordDetails.txt"
                                FileDeletedRecord = 0 //Must to proceed
                            } else {
                                PreviousNameR = "LET_RecordDetails" + NextFileToRenameCounterR.toString() + ".txt"
                                NewNameR = "LET_RecordDetails" + FileNewNameCounterR.toString() + ".txt"
                            }
                            val filePathRecord = getFilesDir()
                            val newFilePathRecord = File(filePathRecord, "/LET_Records")
                            val OldFileRecord = File(newFilePathRecord, PreviousNameR)
                            val NewFileRecord = File(newFilePathRecord, NewNameR)
                            OldFileRecord.renameTo(NewFileRecord)
                            RemainingFilesToRenameR = RemainingFilesToRenameR - 1
                            NextFileToRenameCounterR = NextFileToRenameCounterR + 1
                            FileNewNameCounterR = FileNewNameCounterR + 1
                        }
                        //renaming record file END
                    }
                }
                if (FileExistsAccount == true) {
                    if (FileExistsRecord == true) {
                        Toast.makeText(this, "Account deleted.", Toast.LENGTH_SHORT).show()
                        deleteCheck.isChecked = false
                    } else {
                        Toast.makeText(this, "Failed to delete.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Failed to delete.", Toast.LENGTH_SHORT).show()
                }

                //renaming files END***
            }

            //***************************Counter Reading setting START********************************
            val Level1BugFix = counterScreenCurrent.getText().toString().toInt()
            val Level2BugFix = counterScreenFinal.getText().toString().toInt()
            var NeedBugFix = false
            if (Level1BugFix == 2) {
                if (Level2BugFix == 2) {
                    NeedBugFix = true
                }
            }
            var accountsCounter = 1
            var newFileName = "LET_AccountDetails.txt"
            var C_loopEnd = false
            while (C_loopEnd != true) {
                val filePathCR = getFilesDir() //takes files directory for the app
                val newFilePathCR = File(filePathCR, "/LET_Account") //adding your own folder
                var successCR = true
                if (!newFilePathCR.exists()) { //if does not exist
                    //Counter is not affected as it's folder directory
                    C_loopEnd = true
                }
                if (successCR) {
                    val accountsFile = File(newFilePathCR, newFileName)

                    if (!accountsFile.exists()) { //if does not exist
                        //Counter decreases by one
                        accountsCounter = accountsCounter - 1
                        C_loopEnd = true

                    } else {
                        if (successCR) {
                            // directory exists or already created
                            //Increment Counter
                            accountsCounter = accountsCounter + 1

                        } else {
                            // directory creation is not successful
                            Toast.makeText(this, "Something went wrong while reading file(s).", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                if (C_loopEnd != true) {
                    val textCounter = accountsCounter.toString()
                    newFileName = "LET_AccountDetails" + textCounter + ".txt"
                }
            }
            if (accountsCounter > 1) {
                counterScreenCurrent.setText("1")
                counterScreenFinal.setText(accountsCounter.toString())

            }
            if (NeedBugFix == true) {
                counterScreenCurrent.setText("1")
                counterScreenFinal.setText("1")
            }
            //*************************Counter Reading setting END**********************************

            //File RECORDS READING usage START*****
            var newRecord = ""
            val filePathRR = getFilesDir() //takes files directory for the app
            val newFilePathRR = File(filePathRR , "/LET_Records") //adding your own folder
            var successRR = true
            if (!newFilePathRR.exists()) { //if does not exist
                //create directory but for now do nothing
                accountRecords.setText("No records found yet.")
            }
            if (successRR) {
                var newFile = ""
                val FileCheck = counterScreenCurrent.getText().toString().toInt()
                if (FileCheck == 1) {
                    newFile = "LET_RecordDetails.txt"
                } else {
                    val textFileCheck = FileCheck.toString()
                    newFile = "LET_RecordDetails" + textFileCheck + ".txt"

                }
                val recordsFile = File(newFilePathRR, newFile)

                if (!recordsFile.exists()) { //if does not exist
                    //create and write on to it but for now do nothing
                    accountRecords.setText("No records found yet.")
                    Toast.makeText(this, "Records File does not exist.", Toast.LENGTH_SHORT).show()

                } else {
                    if (successRR) {
                        // directory exists or already created
                        //read from it
                        newRecord = recordsFile.readText() //reads from file
                        accountRecords.setText(newRecord) //displays on screen
                        //Toast.makeText(this, "Records File exist.", Toast.LENGTH_SHORT).show()


                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //File RECORDS READING usage END*****
            //File ACCOUNT READING usage START*****
            val filePathAR = getFilesDir() //takes files directory for the app
            val newFilePathAR = File(filePathAR , "/LET_Account") //adding your own folder
            var successAR = true
            if (!newFilePathAR.exists()) { //if does not exist
                //create directory but for now do nothing
                currentBalance.setText("00.00")
            }
            if (successAR) {
                var newFile = ""
                val FileCheck = counterScreenCurrent.getText().toString().toInt()
                if (FileCheck == 1) {
                    newFile = "LET_AccountDetails.txt"
                } else {
                    val textFileCheck = FileCheck.toString()
                    newFile = "LET_AccountDetails" + textFileCheck + ".txt"

                }
                val accountFile = File(newFilePathAR, newFile)

                if (!accountFile.exists()) { //if does not exist
                    //create and write on to it but for now do nothing
                    currentBalance.setText("00.00")
                    Toast.makeText(this, "Account File does not exist.", Toast.LENGTH_SHORT).show()

                } else {
                    if (successAR) {
                        // directory exists or already created
                        //read from it

                        //Taking details from accounts file START

                        var counterFile = 0
                        var counterLoopEnd = 0
                        val readDetails = accountFile.readLines().toString()

                        var accountTekkenName = ""
                        var currencyTekkenType = ""
                        var amountTekken = ""

                        var smallerLoopEnd = false
                        var loopStart = false


                        while (counterLoopEnd < 3) {
                            if (counterLoopEnd == 0) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        accountTekkenName = accountTekkenName + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }
                            }
                            smallerLoopEnd = false

                            if (counterLoopEnd == 1) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        currencyTekkenType = currencyTekkenType + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }

                            }

                            smallerLoopEnd = false

                            if (counterLoopEnd == 2) {
                                while (smallerLoopEnd != true) {
                                    if (readDetails.substring(counterFile , counterFile + 1) == "?") {
                                        loopStart = false
                                        counterLoopEnd = counterLoopEnd + 1
                                        smallerLoopEnd = true
                                    }
                                    if (readDetails.substring(counterFile , counterFile + 1) == ":") {
                                        loopStart = true
                                        counterFile = counterFile + 1
                                    }
                                    if (loopStart == true) {
                                        amountTekken = amountTekken + readDetails.substring(counterFile , counterFile + 1)
                                    }
                                    counterFile = counterFile + 1
                                }
                            }
                        }

                        accountUser.setText(accountTekkenName)
                        currencyTypeUser.setText(currencyTekkenType)
                        currentBalance.setText(amountTekken)
                        //Taking details from accounts file END

                        //Toast.makeText(this, "Account File exist", Toast.LENGTH_SHORT).show()


                    } else {
                        // directory creation is not successful
                        Toast.makeText(this, "Something went wrong while reading file.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //File ACCOUNT READING usage END*****

        }
        //activity on deleting accounts END

    }
}
