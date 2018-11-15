# Entity and Relationships, participation constraints

- User
  - User -> IS_A -> Student or Professor
  - Student -> IS_A -> Grad or Undergrad
  - Grad -> IS_A -> General or TA of one course
- Course
  - Can have one or more TA
  - Can have only One professor
- TA can be teaching assistant of only one course.
- Professor can enroll one or more students into Course he teaches.
- Topic is a weak entity which depends on Course. Topic has total participation in HAS relation with Course.
- Questions -> IS_A -> Fixed question or Parameterized question.
- Answer is weak entity which depends on Questions. Answer has total participation in HAS relation with Question.
- Question Bank Contains zero or more questions.
- Each Question HAS one Topic.
- Exercise -> IS_A -> Standard or Adaptive
- Each Exercise is made up of exactly one Question Bank.
- Every Exercise is associated with Exactly One Course.
- Student may or may not attempt Exercise.
- Submission Details is Weak entity in ternary relationship with total participation

### List of Keys
  - User – USER_ID
  - Course – C_ID
  - Question – Q_ID
  - Topic – T_ID, C_ID
  - Answers – A_ID, Q_ID
  - Exercise – EX_ID
  - Attempts – AT_ID, USER_ID, EX_ID
  - Question Bank – QBANK_ID
  - Teaches (Course and Professor Relation) – C_ID, USER_ID
  - HAS (Course and Exercise Relation) - C_ID, EX_ID
  - Creates (Course and Professor and Question Bank) – C_ID, USER_ID, QBANK_ID
  - HAS (Course and TA Relation) – C_ID, USER_ID
  - HAS (Question and Topic) – Q_ID, T_ID, C_ID
  - HAS (Topic and Course Relation) – C_ID, T_ID
  - HAS (Question Bank and Exercise Relation) – QBANK_ID, EX_ID
  - HAS (Question and Answer Relation) - A_ID, Q_ID
  - Associated With (Course and Question Bank Relation) - C_ID, QBANK_ID
• Enrolls (Course and Professor and Student Relation) – C_ID, USER_ID(PROF), USER_ID(STUDENT)
• Enrolls (Course and TA and Student Relation) – C_ID, USER_ID(TA), USER_ID(STUDENT)
  
