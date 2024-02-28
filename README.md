# Polybius-Encryption
ADFGVX Encryption

@author: John Sullivan G00425758

@version Java-17


Description

This is a menu driven Java application that encrypts and decrypts a specified directory of text files, by taking in an encryption key from the user. The encryption logic is based on encrypting using 2D arrays and columnar transposition to fractionate the cypher. 


To Run

Configure the jar file with a name that suits you, such as cypher.

Open the terminal/console at the .jar file directory:

java -cp ./cypher.jar ie.atu.sw.Runner

Once the menu is running you can select the options to set your file paths, encryption key and perform the encryption. File paths must be specified by the user. When setting the directories utilise the format path/to/directory. Do not include file name or extension.

Features

•	Specify directories in the form path/to/directory. The method will confirm that your inputs are directories and prompt you if they are not.

•	Set the encryption key using option 3. Your input will be printed to the screen and can also be reviewed by selecting option 8, to show user inputs. 

•	Once the variables are specified by the user, the files can be either encrypted or decrypted by the program. 2 parsers are used in the Parser class. For encryption, the parser will remove all special characters etc, whereas the decryption parser will retain any spaces between the characters.

•	The encryption of the files does not clip characters from the files. If 2D array is uneven, blank space characters are appended so that clipping is not required.

•	To write the files to the specified directory, select option 6. This will prompt you to enter a name for the files such as encrypt or decrypt. There’s no need to include a separator or .txt extension as the program will do this for the user.

•	Inside the options menu, an option exists for the user to create a directory by specifying the path in the form path/to/directory. This will create a directory if the pathway is correct. 




