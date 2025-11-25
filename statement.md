\## \*\*Problem Statement\*\*



Many individuals struggle to keep track of their daily expenses, categorize spending, and monitor monthly budgets. Without proper tracking, it becomes difficult to understand spending habits, identify unnecessary expenses, or maintain financial discipline. Existing tools may be too complex, require an internet connection, or demand advanced financial knowledge.

The \*\*Personal Expense Tracker\*\* addresses this issue by providing a simple, offline, Java-based console application that allows users to record, categorize, and analyze their expenses efficiently.



---



\## \*\*Scope of the Project\*\*



The scope of this project covers the core functionalities required for basic personal financial tracking. It focuses on simplicity, offline usage, and ease of understanding for beginners learning Java. The system will:



\* Allow users to add, list, and delete expenses

\* Support creating and managing categories

\* Provide monthly and category-wise spending reports

\* Enable users to set budgets for categories

\* Store all data locally using CSV-based persistence

\* Operate completely through a console-based user interface



The project does \*\*not\*\* include multi-user support, advanced analytics, or GUI-based design, but these can be added in future enhancements.



---



\## \*\*Target Users\*\*



\* Students who want to track their daily spending

\* Individuals needing a simple, offline finance tracker

\* Beginners learning Java programming and OOP concepts

\* Users who prefer lightweight tools without complex features

\* People who want to set and monitor category-based budgets



---



\## \*\*High-Level Features\*\*



\### \*\*1. Expense Management\*\*



\* Add new expenses with a date, amount, category, and optional description

\* View all recorded expenses

\* Delete expenses by ID



\### \*\*2. Category Management\*\*



\* Add new categories

\* Delete unused categories

\* Prevent deletion if a category is currently in use



\### \*\*3. Budget Management\*\*



\* Set monthly budgets for specific categories

\* Update or remove existing budgets

\* Compare spending against budget limits



\### \*\*4. Reporting System\*\*



\* Monthly spending summary (total + category breakdown)

\* All-time category-wise summary

\* Over-budget warnings



\### \*\*5. Data Persistence\*\*



\* All data stored in CSV files

\* Data automatically loaded and saved for each session

