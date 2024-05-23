Ported Source code of the Slang4.net Compiler ( The Source were earlier @ htpp://slangfordotnet.codeplex.com ) to Java

Build Jar file
Instructions:
File -> Project Structure -> Project Settings -> Artifacts -> Click + (plus sign) -> Jar -> From modules with dependencies...

Select a Main Class (the one with main() method) if you need to make the jar runnable.

Select Extract to the target Jar

Click OK

Click Apply/OK

The above sets the "skeleton" to where the jar will be saved to. To actually build and save it do the following:

Build -> Build Artifact -> Build

Try Extracting the .jar file from:

📦ProjectName
 ┗ 📂out
   ┗ 📂artifacts
     ┗ 📂ProjectName_jar
        ┗ 📜ProjectName.jar
