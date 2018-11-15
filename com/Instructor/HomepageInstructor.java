package com.company.Instructor;import com.company.Login;import com.company.Student.HomepageStudent;import com.company.TA.HomepageTA;import oracle.jdbc.OracleCallableStatement;import sun.jvm.hotspot.ui.action.ShowAction;import java.sql.*;import java.util.*;import java.util.regex.Matcher;import java.util.regex.Pattern;public class HomepageInstructor {    private int input;    private String inputString;    private Connection connection;    private String userId;    private Login lastPage;    Scanner reader = new Scanner(System.in);    public HomepageInstructor(Connection _connection,String _userId, Login _lastPage)    {        show("Welcome Professor!");        this.lastPage=_lastPage;        this.connection=_connection;        this.userId=_userId;        showHomeMenu();    }    public void showHomeMenu()    {        show("Enter 1 to View/Edit Profile");        show("Enter 2 to View/Add Courses");        show("Enter 3 to Enroll/Drop a Student");        show("Enter 4 to Enroll/Drop a TA");        show("Enter 5 to View student details");        show("Enter 6 to View/Add Question Bank");        show("Enter 7 to create Exercise");        show("Enter 8 to View Sample QB queries");        show("Enter 9 to View Report");        show("Enter 0 to go back!");        input=getInt();        if(input==0)        {            lastPage.reopen();        }        if(input==1)        {            viewProfile();        }        if(input==2)        {            viewAddCourses();        }        if(input==3)        {            enrollDropStudent();        }        if(input==4)        {            enrollDropTA();        }        if(input==5)        {            viewStudentReport();        }        if(input==6)        {            viewAddQuestionBank();        }        if(input==7)        {            createExercise();        }        if(input==8)        {            sampleQBqueries();        }        if(input==9)        {            viewHWReport();        }        else        {            showHomeMenu();        }        showHomeMenu();    }    public void viewHWReport()    {        HashMap<Integer,String> courses = new HashMap<Integer, String>();        HashMap<Integer,Integer> homeworks= new HashMap<Integer, Integer>();        try {        OracleCallableStatement statement;        String sp="{call SP_VIEWPROFCOURSES (?,?)}";        statement = (OracleCallableStatement) connection.prepareCall(sp);        statement.setString(1,userId.toLowerCase());        statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);        statement.execute();        int i=1;        ResultSet rs = statement.getCursor(2);        if(rs!=null) {            while (rs.next())            {                show(i+ " : CourseID : " + rs.getString(1) +" :Course Name : " + rs.getString(2));                courses.put(i,rs.getString(1));                i++;            }        }        } catch (SQLException e) {            e.printStackTrace();        }        show("Enter the course number you want to see the report:");        input=getInt();        String _courseID=courses.get(input);        try {            OracleCallableStatement statement;            String sp = "{call SP_GETFINALSCOREFORALL" + " (?,?)}";            statement = (OracleCallableStatement) connection.prepareCall(sp);            statement.setString(1, _courseID);            statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);            statement.execute();            ResultSet rs = statement.getCursor(2);            if (rs != null) {                show("Ex Id : name : score");                while (rs.next()) {                    show(rs.getString(3)+ ":"+rs.getString(1) +" "+rs.getString(2)+ " : "+rs.getString(4));                }            }        }        catch (Exception e)        {}        showHomeMenu();    }    public void sampleQBqueries()    {        show("Enter 1 to View people who attempted hw1 and not hw2,0 to go back");        show("Enter 2 to View homeworks of all students in CSC540,0 to go back");        show("Enter 3 to studens enrolled in all the courses,0 to go back");        input=getInt();        if(input==1)        {            try {            OracleCallableStatement statement;            String sp="{call SP_ATTEMPT_HW1_NOT_HW2 (?,?)}";            statement = (OracleCallableStatement) connection.prepareCall(sp);            statement.setString(1,"CSC540");            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);            statement.execute();            int i=1;            ResultSet rs = statement.getCursor(2);            if(rs!=null) {                while (rs.next())                {                    show(rs.getString(1) +" "+rs.getString(2));                }            }            } catch (SQLException e) {                e.printStackTrace();            }        }        if(input==2)        {            show("Enter courseID to view data:");            inputString = getStr();            try {                OracleCallableStatement statement3;                String sp3 = "{call SP_SHOWATTEMPTFORSUBJECT" + " (?,?)}";                statement3 = (OracleCallableStatement) connection.prepareCall(sp3);                statement3.setString(1,inputString );                statement3.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);                statement3.execute();                ResultSet rs3 = statement3.getCursor(2);                int i=1;                while (rs3.next()) {                    show("Attempt: "+i);                    show("UserID: "+rs3.getString(2));                    show("HomeworkID: "+rs3.getInt(3));                    show("Obtained score: "+rs3.getInt(4));                    show("Time of attempt: "+rs3.getString(5));                    i++;                }            }            catch (Exception e)            {}        }        if(input==3)        {            try {                OracleCallableStatement statement3;                String sp3 = "{call SP_SHOWATTEMPTFORSUBJECT" + " (?)}";                statement3 = (OracleCallableStatement) connection.prepareCall(sp3);                statement3.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);                statement3.execute();                ResultSet rs3 = statement3.getCursor(1);                int i=1;                while (rs3.next()) {                    show("CourseId : "+rs3.getString(1));                    show("CourseName: "+rs3.getString(2));                    show("No of students: "+rs3.getInt(3));                }            }            catch (Exception e)            {}        }        else        {            showHomeMenu();        }        showHomeMenu();    }    public void viewAddQuestionBank()    {        HashMap<Integer,String> courseMap= new HashMap<Integer, String>();        HashMap<Integer,String> topics= new HashMap<Integer, String>();        OracleCallableStatement statement;        String sp="{call SP_VIEWPROFCOURSES (?,?)}";        try {            statement = (OracleCallableStatement) connection.prepareCall(sp);            statement.setString(1,userId.toLowerCase());            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);            statement.execute();            int i=1;            ResultSet rs = statement.getCursor(2);            if(rs!=null) {                while (rs.next())                {                    show(i+ " : CourseID : " + rs.getString(1) +" :Course Name : " + rs.getString(2));                    courseMap.put(i,rs.getString(1));                    i++;                }            }        } catch (SQLException e) {            e.printStackTrace();        }        show("Please enter the course number which QuestionBank you would like to view:");        show("Enter 0 to go back");        input=getInt();        String _courseIdSelected=courseMap.get(input);        if(input==0)            showHomeMenu();        show("Enter 1 to view all questions, 2 to add a question:");        input=getInt();        if(input==1)        {            OracleCallableStatement statement2;            String sp2="{call SP_GET_ALL_QUES_FROM_QB (?,?)}";            try {                statement2 = (OracleCallableStatement) connection.prepareCall(sp2);                statement2.setString(1,_courseIdSelected);                statement2.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);                statement2.execute();                int i=1;                ResultSet rs2 = statement2.getCursor(2);                if(rs2!=null) {                    while (rs2.next())                    {                        show(i+ ") " + rs2.getString(2) );                        i++;                    }                }            } catch (SQLException e) {                e.printStackTrace();            }            showHomeMenu();        }        else        {            show("Select from the below topics:");            OracleCallableStatement statement2;            String sp2="{call SP_VIEWALLTOPICS (?,?)}";            try {                statement2 = (OracleCallableStatement) connection.prepareCall(sp2);                statement2.setString(1,_courseIdSelected);                statement2.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);                statement2.execute();                int i=1;                ResultSet rs2 = statement2.getCursor(2);                if(rs2!=null) {                    while (rs2.next())                    {                        show(i+ " : TopicID : " + rs2.getString(1) +" :Topic Name : " + rs2.getString(2));                        topics.put(i,rs2.getString(2));                        i++;                    }                }            } catch (SQLException e) {                e.printStackTrace();            }            show("Enter the Question topic:");            input=getInt();            String _topic=topics.get(input);            show("Enter the Difficulty Level:(1 to 6)");            input=getInt();            int _difficultyLevel=input;            show("Enter the Question type(Standard: S, Paramenter, P):");            inputString=getStr();            while(!(inputString.equalsIgnoreCase("S") || (inputString.equalsIgnoreCase("P"))))            {                show("Enter question  type again:");                inputString=getStr();            }            String _questionType=inputString;            if(_questionType.equalsIgnoreCase("S"))            {                show("Enter question :");                inputString=getStr();                String _question=inputString;                show("Enter hint for the question :");                inputString=getStr();                String _questionHint=inputString;                show("Enter detailed solution for the question :");                inputString=getStr();                String _solution=inputString;                show("Enter correct answer for the question :");                inputString=getStr();                String _correctAnswer=inputString;                show("Enter incorrect option 1 for the question :");                inputString=getStr();                String _incorrectAns1=inputString;                show("Enter incorrect option 2 for the question :");                inputString=getStr();                String _incorrectAns2=inputString;                show("Enter incorrect option 3 for the question :");                inputString=getStr();                String _incorectAns3=inputString;                OracleCallableStatement statement3;                String sp3="{call SP_ADDQUESTION (?,?,?,?,?,?,?,?,?,?,?,?,?)}";                try {                    statement3 = (OracleCallableStatement) connection.prepareCall(sp3);                    statement3.setString(1,_courseIdSelected);                    //show(_courseIdSelected);                    statement3.setString(2,_topic);                    //show(_topic);                    statement3.setInt(3,_difficultyLevel);                    //show(_difficultyLevel+"");                    statement3.setString(4,_questionHint);                    //show(_questionHint);                    statement3.setString(5,_question);                    statement3.setString(6,null);                    //show(_question);                    statement3.setInt(7,0);                    statement3.setString(8,_solution);                    //show(_solution);                    statement3.setString(9,_correctAnswer);                    //show(_correctAnswer);                    statement3.setString(10,_incorrectAns1);                    //show(_incorrectAns1);                    statement3.setString(11,_incorrectAns2);                    //show(_incorrectAns2);                    statement3.setString(12,_incorectAns3);                    //show(_incorectAns3);                    statement3.registerOutParameter(13,oracle.jdbc.OracleTypes.CURSOR);                    statement3.execute();                    ResultSet rs3 = statement3.getCursor(13);                    int _questionId=0;                    if(rs3!=null) {                        while (rs3.next()) {                            _questionId= rs3.getInt(1);                        }                        show(_questionId+"");                    }                } catch (SQLException e) {                    e.printStackTrace();                }            }            else            {                show("Enter root question text with 'parameters in '[]'  Ex. [x][y]:");                inputString=getStr();                String _questionRoot=inputString;                show("Enter general hint for the question :");                inputString=getStr();                String _questionHint=inputString;                show("Enter detailed general solution for the question :");                inputString=getStr();                String _solution=inputString;                List<String> matchList = new ArrayList<String>();                Pattern regex = Pattern.compile("\\(([^)]+)\\)");                Matcher regexMatcher = regex.matcher(_questionRoot);                while (regexMatcher.find()) {//Finds Matching Pattern in String                    matchList.add(regexMatcher.group(1));//Fetching Group from String                }                show("How many set of parameters are you going to enter?");                input=getInt();                int _parameterSet=input;                String _question="";                for(int i=0;i<_parameterSet;i++)                {                    show("Enter parameterset : "+i);                    String _tempRootText=_questionRoot;                    HashMap<String,String> parameterMap= new HashMap<String, String>();                    for(String str:matchList) {                        String temp= "("+str+")";                        show("Enter value for "+str);                        inputString=getStr();                        String val= inputString;                        _tempRootText=_tempRootText.replace(temp,val);                    }                    show("Enter answers for the above given parameters:");                    show("Enter correct answer for the question :");                    inputString=getStr();                    String _correctAnswer=inputString;                    show("Enter incorrect option 1 for the question :");                    inputString=getStr();                    String _incorrectAns1=inputString;                    show("Enter incorrect option 2 for the question :");                    inputString=getStr();                    String _incorrectAns2=inputString;                    show("Enter incorrect option 3 for the question :");                    inputString=getStr();                    String _incorectAns3=inputString;                    OracleCallableStatement statement3;                    String sp3="{call SP_ADDQUESTION (?,?,?,?,?,?,?,?,?,?,?,?,?)}";                    try {                        statement3 = (OracleCallableStatement) connection.prepareCall(sp3);                        statement3.setString(1,_courseIdSelected);                        statement3.setString(2,_topic);                        statement3.setInt(3,_difficultyLevel);                        statement3.setString(4,_questionHint);                        statement3.setString(5,_tempRootText);                        statement3.setString(6,_questionRoot);                        statement3.setInt(7,1);                        statement3.setString(8,_solution);                        statement3.setString(9,_correctAnswer);                        statement3.setString(10,_incorrectAns1);                        statement3.setString(11,_incorrectAns2);                        statement3.setString(12,_incorectAns3);                        statement3.registerOutParameter(13,oracle.jdbc.OracleTypes.CURSOR);                        statement3.execute();                        ResultSet rs3 = statement3.getCursor(13);                        int _questionId=0;                        if(rs3!=null) {                            while (rs3.next()) {                                _questionId= rs3.getInt(1);                            }                            //show(_questionId);                        }                    } catch (SQLException e) {                        e.printStackTrace();                    }                }                show("Question added to question bank!");            }        }    }    public void viewStudentReport() {        try {            String studentID="";            String studentFirstName="";            String studentLastName="";            show("Please enter studentID or student's full name to view the report : Ex. aadp2 or bruce banner");            inputString = getStr();            String[] elements = inputString.split(" ");            if(elements.length==1)            {                studentID = elements[0];            }            ////////////////////////////////////////////////////////////////////////////////////////////////////////////            OracleCallableStatement statement;            String sp="{call SP_VIEWPROFILE (?,?)}";            statement = (OracleCallableStatement) connection.prepareCall(sp);            statement.setString(1,studentID);            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);            statement.execute();            ResultSet rs = statement.getCursor(2);            if(rs!=null) {                while (rs.next())                {                    studentFirstName=rs.getString(1);                    studentLastName=rs.getString(2);                    show("UserID :  " + studentID);                    show("FirstName : " + studentFirstName);                    show("LastName : " + studentLastName);                }            }            ////////////////////////////////////////////////////////////////////////////////////////////////////////////            OracleCallableStatement statement2;            String sp2="{call SP_VIEWPROFSTUDENTCOURSES" + " (?,?,?)}";            statement2 = (OracleCallableStatement) connection.prepareCall(sp2);            statement2.setString(1,userId.toLowerCase());            statement2.setString(2,studentID);            statement2.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);            statement2.execute();            ResultSet rs2 = statement2.getCursor(3);            HashMap<Integer,String> courses= new HashMap<Integer, String>();            int i=1;            String courseName="";            String courseId="";            if(rs2!=null) {                while (rs2.next())                {                    courseId=rs2.getString(1);                    courseName=rs2.getString(2);                    courses.put(i,courseId);                    show(i+" : " + courseName);                    i++;                }            }            show("Enter the course number you want to view for a student:");            input=getInt();           ////////////////////////////////////////////////////////////////////////////////////////////////////////////            if(input!=0)            {                OracleCallableStatement statement3;                String sp3="{call SP_VIEWSTUDENTCOURSESCORES" + " (?,?,?)}";                statement3 = (OracleCallableStatement) connection.prepareCall(sp3);                statement3.setString(1,studentID);                statement3.setString(2,(String)courses.get(input));                statement3.registerOutParameter(3,oracle.jdbc.OracleTypes.CURSOR);                statement3.execute();                ResultSet rs3 = statement3.getCursor(3);                HashMap<String,String> homeworks= new HashMap<String, String>();                String homework="";                String score="";                if(rs3!=null) {                    while (rs3.next())                    {                        homework=rs3.getString(1);                        score=rs3.getString(2);                        show("Hemowork  "+homework+" : " + score);                    }                }                show("Please any key to return:");            }            ////////////////////////////////////////////////////////////////////////////////////////////////////////////            inputString=getStr();            showHomeMenu();        } catch (SQLException e) {            e.printStackTrace();        }    }    public void createExercise()    {        HashMap<Integer,String> topics= new HashMap<Integer, String>();        HashMap<Integer,Integer> topicIDs= new HashMap<Integer, Integer>();        String courseID="CSC540",exerciseName="ex 5",startDate="12-DEC-2017",endDate="15-DEC-2017",exerciseType="S",scoringPolicy="L";        int totalQ=10,tries=9,correctPoints=3,penalty=-2,topic=0;        show("Enter courseId");        courseID=getStr();        show("Enter topicNo  from the following topics");        show("Select from the below topics:");        try {        OracleCallableStatement statement2;        String sp2="{call SP_VIEWALLTOPICS (?,?)}";        statement2 = (OracleCallableStatement) connection.prepareCall(sp2);        statement2.setString(1,courseID);        statement2.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);        statement2.execute();        int i=1;        ResultSet rs2 = statement2.getCursor(2);        if(rs2!=null) {            while (rs2.next())            {                show(i+ " ::Topic Name : " + rs2.getString(2));                topics.put(i,rs2.getString(2));                topicIDs.put(i,rs2.getInt(1));                i++;            }        }        } catch (SQLException e) {            e.printStackTrace();        }        topic=getInt();        show("Enter exerciseName");        exerciseName=getStr();        show("Enter startDate");        startDate=getStr();        show("Enter endDate(Ex: 12-DEC-2017)");        endDate=getStr();        show("Enter total questions");        totalQ=getInt();        show("Enter max tries");        tries=getInt();        show("Enter correct points");        correctPoints=getInt();        show("Enter penalty");        penalty=getInt();        show("Enter Scoring Policy(L,M,A)");        scoringPolicy=getStr();        show("Enter exerciseType (S: standard, A1: Adaptive)");        exerciseType=getStr();        int exerciseId=0;        OracleCallableStatement statement;        String sp="{call SP_CREATEEXERCISE(?,?,?,?,?,?,?,?,?,?,?,?,?)}";        try {            statement = (OracleCallableStatement) connection.prepareCall(sp);            statement.setString(1,userId);            statement.setString(2,courseID);            statement.setString(3,topics.get(topic));            statement.setString(4,exerciseName);            statement.setString(5,startDate);            statement.setString(6,endDate);            statement.setInt(7,totalQ);            statement.setInt(8,tries);            statement.setInt(9,correctPoints);            statement.setInt(10,penalty);            statement.setString(11,exerciseType);            statement.setString(12,scoringPolicy);            statement.registerOutParameter(13,Types.VARCHAR);            statement.execute();            exerciseId= statement.getInt(13);            show(exerciseId+"");        } catch (SQLException e) {            e.printStackTrace();        }        HashMap<Integer,Integer> questions= new HashMap<Integer, Integer>();        if(exerciseType.equalsIgnoreCase("s"))        {            try {                OracleCallableStatement statement2;                String sp2="{call SP_GET_QID_FOR_TOPIC (?,?)}";                statement2 = (OracleCallableStatement) connection.prepareCall(sp2);                statement2.setInt(1,topicIDs.get(topic));                statement2.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);                statement2.execute();                int i=1;                ResultSet rs2 = statement2.getCursor(2);                if(rs2!=null) {                    while (rs2.next())                    {                        show(i+ ") " + rs2.getString(2) );                        questions.put(i,rs2.getInt(1));                        i++;                    }                }            } catch (SQLException e) {                e.printStackTrace();            }            for(int i=0;i<totalQ;i++)            {                show("Please enter the qNO to insert in the exercise:");                input=getInt();                try {                    OracleCallableStatement statement2;                    String sp2="{call SP_MAP_EXID_QID (?,?)}";                    statement2 = (OracleCallableStatement) connection.prepareCall(sp2);                    statement2.setInt(1,exerciseId);                    statement2.setInt(2,questions.get(input));                    statement2.execute();                } catch (SQLException e) {                    e.printStackTrace();                }            }        }        showHomeMenu();    }    public void viewProfile()    {        String firstName="";        String lastName="";        OracleCallableStatement statement;        String sp="{call SP_VIEWPROFILE (?,?)}";        try {            statement = (OracleCallableStatement) connection.prepareCall(sp);            statement.setString(1,userId.toLowerCase());            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);            statement.execute();            ResultSet rs = statement.getCursor(2);            if(rs!=null) {                while (rs.next())                {                    firstName=rs.getString(1);                    lastName=rs.getString(2);                    show("First Name : " + firstName);                    show("Last Name : " + lastName);                    show("UserID : " + userId+"\n");                }            }        } catch (SQLException e) {            e.printStackTrace();        }        show("Enter 1 to Edit First Name, 2 for Last Name ");        show("Enter any other number to go back");        input=getInt();        if(input==0)        {            showHomeMenu();        }        if(input==1)        {            show("Please enter your first name:");            inputString=getStr();            //SP_Change profile details            OracleCallableStatement statement2;            String sp2="{call SP_EDITPROFILE (?,?,?)}";            try {                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.setString(2,inputString);                statement.setString(3,lastName);                statement.execute();            } catch (SQLException e) {                e.printStackTrace();            }        }        if(input ==2)        {            show("Please enter your last name:");            inputString=getStr();            //SP_Change profile details            OracleCallableStatement statement2;            String sp2="{call SP_EDITPROFILE (?,?,?)}";            try {                statement2 = (OracleCallableStatement) connection.prepareCall(sp2);                statement2.setString(1,userId.toLowerCase());                statement2.setString(2,firstName);                statement2.setString(3,inputString);                statement2.execute();            } catch (SQLException e) {                e.printStackTrace();            }        }        else        showHomeMenu();    }    public void enrollDropStudent()    {        String studentFirstName="";        String studentLastName="";        String studentID="";        String courseID="";        show("Press 1 to enroll a student");        show("Press 2 to drop a student");        input=  getInt();        if(input==1)        {            // Enroll a student            show("Please enter the details of the student to enroll: UserId CourseID , Ex: aadp1 bruce banner CSC540");            inputString=getStr();            String[] elements= inputString.split(" ");            int i=0;            studentID= elements[0];            courseID= elements[1];            try{                OracleCallableStatement statement;                String sp="{call SP_ENROLL_STUDENT(?,?,?,?)}";                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.setString(2,studentID);                statement.setString(3,courseID);                statement.registerOutParameter(4,Types.VARCHAR);                statement.execute();                String  feedback= statement.getString(4);                show(feedback);            } catch (SQLException e) {                e.printStackTrace();            }        }        if(input==2)        {            // Drop a student            show("Please enter the details of the student to drop: UserId CourseID , Ex: aadp1 CSC540");            inputString=getStr();            String[] elements= inputString.split(" ");            int i=0;            while(elements[i].equals(""))                i++;            studentID= elements[i];            i++;            while(elements[i].equals(""))                i++;            courseID= elements[i];            OracleCallableStatement statement;            String sp="{call SP_DROP_STUDENT(?,?,?,?)}";            try {                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.setString(2,studentID);                statement.setString(3,courseID);                statement.registerOutParameter(4,Types.VARCHAR);                statement.execute();                String feedback=  statement.getString(4);                show(feedback);            } catch (SQLException e) {                e.printStackTrace();            }        }        else        {            showHomeMenu();        }    }    public void enrollDropTA()    {        show("Press 1 to enroll a TA");        show("Press 2 to drop a TA");        input=  getInt();        if(input==1)        {            // Enroll a student            show("Please enter the details of the TA to enroll: UserId  CourseID , Ex: aadp1 CSC540");            inputString=getStr();            String[] elements= inputString.split(" ");            int i=0;            while(elements[i].equals(""))                i++;            String TAID= elements[i];            i++;            String courseID= elements[i];            OracleCallableStatement statement;            String sp="{call SP_ADDTA(?,?,?)}";            try {                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.setString(2,TAID);                statement.setString(3,courseID);                statement.execute();            } catch (SQLException e) {                e.printStackTrace();            }        }        if(input==2)        {            // Drop a student            show("Please enter the details of the TA to drop: UserId CourseID , Ex: aadp1 CSC540");            inputString=getStr();            String[] elements= inputString.split(" ");            int i=0;            while(elements[i].equals(""))                i++;            String TAID= elements[i];            i++;            while(elements[i].equals(""))                i++;            String courseID= elements[i];            OracleCallableStatement statement;            String sp="{call SP_DROPTA(?,?,?)}";            try {                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.setString(2,TAID);                statement.setString(3,courseID);                statement.execute();            } catch (SQLException e) {                e.printStackTrace();            }        }        else        {            showHomeMenu();        }    }    public void viewAddCourses()    {        show("Press 1 to View exsiting courses, and 2 to add a new Course .... Press 0 to go back");        input=getInt();        if(input==1) {            OracleCallableStatement statement;            String sp="{call SP_VIEWPROFCOURSES (?,?)}";            try {                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);                statement.execute();                ResultSet rs = statement.getCursor(2);                if(rs!=null) {                    while (rs.next())                    {                        show("CourseID : " + rs.getString(1));                        show("Course Name : " + rs.getString(2));                        show("Start Date : " + rs.getString(3));                        show("End Date : " + rs.getString(4));                        show("Professor Name : " + rs.getString(5));                    }                }            } catch (SQLException e) {                e.printStackTrace();            }        }        if(input==2)        {            // Add  Course            show("Please enter the details of the course to drop: ");            show("Enter CourseID");            String courseID=getStr();            show("Enter CourseName");            String courseName=getStr();            show("Enter StartDate");            String startDate=getStr();            show("Enter EndDate");            String endDate=getStr();            OracleCallableStatement statement;            String sp="{call SP_ADDCOURSE(?,?,?,?,?)}";            try {                statement = (OracleCallableStatement) connection.prepareCall(sp);                statement.setString(1,userId.toLowerCase());                statement.setString(2,courseID);                statement.setString(3,courseName);                statement.setString(4,startDate);                statement.setString(5,endDate);                statement.execute();            } catch (SQLException e) {                e.printStackTrace();            }        }        else        {            showHomeMenu();        }    }    public void show(String statement)    {        System.out.println(statement);    }    public String getStr()    {        // Reading from System.in        String str = reader.nextLine();        if(str.equals(""))            str = reader.nextLine(); // Scans the next token of the input as an int.        return str;    }    public void reopen()    {        this.showHomeMenu();    }    public int getInt()    { // Reading from System.in        String inputString="";        int input=-1;        while (input==-1)        {            try            {                inputString = reader.nextLine();                input=Integer.parseInt(inputString);            }            catch (Exception e)            {            }        }         // Scans the next token of the input as an int.        return input;    }    public final  void clearScreen()    {        try        {            final String os = System.getProperty("os.name");            if (os.contains("Windows"))            {                Runtime.getRuntime().exec("cls");            }            else            {                Runtime.getRuntime().exec("clear");            }        }        catch (final Exception e)        {            //  Handle any exceptions.        }    }}