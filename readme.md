\## \*\*Project Title\*\*



\# \*\*Personal Expense Tracker (Java Console Application)\*\*



---



\## \*\*Overview\*\*



The \*\*Personal Expense Tracker\*\* is a beginner-friendly, console-based Java application designed to help users record, organize, and analyze their daily expenses. It allows users to add expenses, categorize them, set budgets, generate monthly reports, and maintain records through CSV-based storage.



The project demonstrates \*\*Java OOP\*\*, \*\*modular design\*\*, \*\*file handling\*\*, \*\*CSV persistence\*\*, and \*\*simple reporting\*\*. It follows the academic project requirements from the provided instructions, including clear modules, workflow, documentation, and testing structure.



---



\## \*\*Features\*\*



\### \*\*1. Expense Management\*\*



\* Add new expenses with date, amount, category, and description

\* View all recorded expenses

\* Delete expenses by ID



\### \*\*2. Category \& Budget Management\*\*



\* Create and delete categories

\* Assign monthly budgets per category

\* Automatic validation to prevent deleting categories in use



\### \*\*3. Reporting\*\*



\* Monthly summary report (total spent + category breakdown)

\* Category-wise all-time report

\* Budget vs actual spending indicators



\### \*\*4. Data Persistence\*\*



\* All data stored in CSV files:



&nbsp; \* `expenses.csv`

&nbsp; \* `categories.csv`

&nbsp; \* `budgets.csv`



\### \*\*5. Error Handling\*\*



\* Invalid inputs handled safely

\* Date format validation

\* Prevention of negative/zero expense amounts



---



\## \*\*Technologies / Tools Used\*\*



\* \*\*Java 17+\*\*

\* \*\*Java Collections Framework\*\*

\* \*\*File I/O (CSV Storage)\*\*

\* \*\*Object-Oriented Programming (OOP)\*\*

\* \*\*UML \& Architectural Diagrams (for documentation)\*\*

\* \*\*Git/GitHub for version control\*\*



---



\## \*\*Steps to Install \& Run the Project\*\*



\### \*\*Clone or Download the Repository\*\*



```bash

git clone https://github.com/your-username/expense-tracker-java.git

```



\### \*\*Navigate into the project\*\*



```bash

cd expense-tracker-java

```



\### \*\*Compile the Source Files\*\*



Make sure you have Java installed:



```bash

javac -d out src/com/expensetracker/\*\*/\*.java src/com/expensetracker/\*.java

```



\### \*\*Run the Application\*\*



```bash

java -cp out com.expensetracker.Main

```



\### \*\*Data Storage Location\*\*



The app stores CSV files automatically in:



```

C:/Users/<yourname>/.expense\_tracker\_data/

```



or on Linux/macOS:



```

/home/<yourname>/.expense\_tracker\_data/

```



---



\## \*\*Instructions for Testing\*\*



You can test functionality by manually walking through:



\### \*\*1. Expense Tests\*\*



\* Add a valid expense

\* Try invalid date

\* Try entering zero or negative amount

\* Delete an existing and non-existing expense



\### \*\*2. Category Tests\*\*



\* Add category

\* Try adding duplicate category

\* Attempt deleting a category currently used by expenses



\### \*\*3. Budget Tests\*\*



\* Set budget for category

\* Test overspending to see “Over Budget” warnings

\* Delete budget



\### \*\*4. Report Tests\*\*



\* Generate monthly report

\* Generate category summary



---



\## \*\*Author\*\*



\* Name: Jayesh Mandal

\* RegNo: 24MIM10144

&nbsp; 

---



\### \_Overview/Note\_

\* if using java version > 22

&nbsp; you can skip compilation and go to "src" folder in CMD and type

&nbsp; ```

&nbsp; java com/expensetracker/Main.java

&nbsp; ```

&nbsp; and the file should work without compiltion.



