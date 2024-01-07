
## How to work in team:

Once you have ticket on you and you ready to start work on it
drag your ticket from colunm **Ready** to the **In progress**.
This way all team mates can see on what you working on.
***
### Git, branches and commits:

// Get up to date code from main branch  
**(main)** *git pull*

// Create your own branch  
**(main)** *git checkout -b ticket-1*

// Add your branch to github  
**(ticket-1)** *git push -u origin ticket-1*

// do your work  
**(ticket-1)** *" do your work "*

// Get updates from main branch  
**(ticket-1)** *git pull origin main*

// resolve conflicts if you have  
**(ticket-1)** *" resolve conflicts if you have "*

// Add your changes to git  
**(ticket-1)** *git add .*

// Commit your changes  
**(ticket-1)** *git commit -m "your message"*

// Push your changes to github  
**(ticket-1)** *git push*

// Switch to main branch  
**(ticket-1)** *git switch main*

// Get up to date code from main branch  
**(main)** *git pull*
***

When you done the process, you need to create **Pull Request (PR)** for your branch
and move your ticket from column **In progress** to the **In review**.  
In this stage I will review your code and make a comments if some additional work/adjustments are required.
It's also good opportunity for all team mates review the code and get familiar with the changes that you made.
We consider that all team must be familiar with code, so all of as must review the code and **create comment DONE below PR**.
After It I will **merge** it to the **main** branch and will move your ticket from **In progress** to **Done**
