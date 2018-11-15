package com.company.Student;

import com.company.Login;
import oracle.jdbc.OracleCallableStatement;

import javax.print.attribute.HashAttributeSet;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class HomepageStudent {
    private String username;
    private String password;
    private Connection connection;
    private String userId;
    private int input;
    private Login lastPage;
    Scanner reader = new Scanner(System.in);
    public HomepageStudent(Connection _connection,String _userId, Login _lastPage)
    {
        show("Welcome Student!!");
        this.lastPage=_lastPage;
        this.connection=_connection;
        this.userId=_userId;
        showHomeMenu();
    }
    public void showHomeMenu() {
        show("Enter 1 to View/Edit Profile");
        show("Enter 2 to View Courses");
        show("Enter 0 to go back");
        show("Enter your option");
        input = getInt();
        if (input == 1) {
            viewProfile();
        }
        if (input == 2) {
            viewCourses();
        }
        else if (input==0)
            {
            lastPage.reopen();
        }
        showHomeMenu();
    }


    public void viewProfile()
    {
        try {
            OracleCallableStatement statement;
            String sp="{call SP_VIEWPROFILE (?,?)}";
            statement = (OracleCallableStatement) connection.prepareCall(sp);
            statement.setString(1,userId);
            statement.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
            statement.execute();
            ResultSet rs = statement.getCursor(2);
            if(rs!=null) {
                while (rs.next())
                {
                    String studentFirstName=rs.getString(1);
                    String studentLastName=rs.getString(2);
                    show("UserID :  " + userId);
                    show("FirstName : " + studentFirstName);
                    show("LastName : " + studentLastName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void viewCourses()
    {
        String _courseId;
        Integer _homeworkId;
        int _attemptId=-1;
        HashMap<Integer, String> courses = new HashMap<Integer, String>();
        HashMap<Integer,Integer> attempts = new HashMap<Integer, Integer>();
        HashMap<Integer,Integer> homeworks = new HashMap<Integer, Integer>();
        try {
            OracleCallableStatement statement;
            String sp = "{call SP_ENROLLEDCOURSES" + " (?,?)}";

            statement = (OracleCallableStatement) connection.prepareCall(sp);
            statement.setString(1, userId.toLowerCase());
            statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            statement.execute();
            ResultSet rs = statement.getCursor(2);

            int i = 1;
            String courseName = "";
            String courseId = "";
            if (rs != null) {
                while (rs.next()) {
                    courseId = rs.getString(1);
                    courseName = rs.getString(2);
                    courses.put(i, courseId);
                    show(i + " : " + courseName);
                    i++;
                }
            }
        }
        catch (Exception e)
        {}
        show("Enter Course number to view homeworks");
        show("Enter 0 to go back");
        input=getInt();
        if(input==0)
            showHomeMenu();
        else
        {
            _courseId=courses.get(input);

            show("Enter 1 to view open homeworks, 2 to view past homeworks");
            input=getInt();
            if(input==1)
            {
                try {
                    OracleCallableStatement statement;
                    String sp = "{call SP_CURRENTOPENHWS" + " (?,?,?)}";
                    statement = (OracleCallableStatement) connection.prepareCall(sp);
                    statement.setString(1, _courseId);
                    statement.setInt(2,1);
                    statement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
                    statement.execute();
                    ResultSet rs = statement.getCursor(3);
                    int i = 1;
                    String hwName = "";
                    int hwId =0;
                    if (rs != null) {
                        while (rs.next()) {
                            hwId = rs.getInt(1);
                            hwName = rs.getString(2);
                            homeworks.put(i, hwId);
                            show(i + " : " + hwName);
                            i++;
                        }
                    }
                }
                catch (Exception e)
                {}

                show("Enter hwID to attempt it, 0 to go back");
                input=getInt();

                if(input==0)
                    showHomeMenu();

                else
                {
                    int _hwSelected=homeworks.get(input);

                    String hwName = "";
                    String scoringPolicy ="";
                    String hwType ="";
                    String topicName ="";
                    int topicId=0;
                    int hwId=0;
                    int totalQuestions=0;
                    int retries=0;
                    int correctPts=0;
                    int penalty=0;
                    int attemptsMade=0;
                    int attemptsLeft=0;
                    try {
                        OracleCallableStatement statement;
                        String sp = "{call SP_GET_EXERCISE_DETAILS" + " (?,?)}";
                        statement = (OracleCallableStatement) connection.prepareCall(sp);
                        statement.setInt(1, _hwSelected);
                        statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
                        statement.execute();
                        ResultSet rs = statement.getCursor(2);
                        int i = 1;

                        if (rs != null) {
                            while (rs.next()) {
                                scoringPolicy = rs.getString(9);
                                hwType= rs.getString(10);
                                topicName = rs.getString(1);
                                hwName = rs.getString(2);
                                hwId=rs.getInt(1);
                                topicId=rs.getInt(14);
                                totalQuestions=rs.getInt(5);
                                retries=rs.getInt(6);
                                correctPts=rs.getInt(7);
                                penalty=rs.getInt(8);
                            }
                        }
                    }
                    catch (Exception e)
                    {}

                    try {
                        OracleCallableStatement statement;
                        String sp = "{call SP_GETATTEMPTCOUNT" + " (?,?,?,?,?)}";
                        statement = (OracleCallableStatement) connection.prepareCall(sp);
                        statement.setString(1, userId);
                        statement.setInt(2,_hwSelected);
                        statement.registerOutParameter(3, Types.VARCHAR);
                        statement.registerOutParameter(4, Types.INTEGER);
                        statement.registerOutParameter(5, Types.INTEGER);
                        statement.execute();
                        attemptsMade=statement.getInt(4);
                        attemptsLeft=statement.getInt(5);
                        show(attemptsMade +" : "+attemptsLeft);
                    }
                    catch (Exception e)
                    {}
                    if(attemptsLeft==0)
                    {
                        show("Cannot be attempted again");
                        showHomeMenu();
                    }
                    else
                    {
                        int[] qId= new int[10000];
                        int totalQues=0;
                        int[] qNo= new int[10000];
                        int[] qDifficultyLevel= new int[10000];
                        String[] qHint= new String[10000];
                        String[] question = new String[10000];
                        String[] qSolution = new String[10000];
                        String[] qrootText= new String[10000];
                        String[] qAnswers= new String[10000];
                        int[] qIsCorrect = new int[10000];
                        try
                        {
                            OracleCallableStatement statement;
                            String sp = "{call SP_GETQUESTIONSFORTOPIC" + " (?,?)}";
                            statement = (OracleCallableStatement) connection.prepareCall(sp);
                            statement.setInt(1, topicId);
                            statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
                            statement.execute();
                            ResultSet rs = statement.getCursor(2);
                            int i = 0;

                            while (rs.next()) {
                                qHint[i] = rs.getString(3);
                                question[i]= rs.getString(4);
                                if(rs.getString(6)!=null)
                                    qrootText[i] = rs.getString(6);
                                else
                                    qrootText[i]=null;
                                qSolution[i] = rs.getString(5);
                                qAnswers[i] = rs.getString(7);
                                qNo[i]=i;
                                qId[i]=rs.getInt(1);
                                qDifficultyLevel[i]=rs.getInt(2);
                                qIsCorrect[i]=rs.getInt(8);
                                i++;
                            }
                            totalQues=i;
                        }

                        catch (Exception e)
                        {}
                        _attemptId=-1;
                        try {
                            OracleCallableStatement statement;
                            String sp = "{call SP_SET_ATTEMPT" + " (?,?,?,?,?,?)}";
                            statement = (OracleCallableStatement) connection.prepareCall(sp);
                            statement.setString(1, _courseId);
                            statement.setInt(2,_hwSelected);
                            statement.setInt(3,_attemptId);
                            statement.setString(4,userId);
                            statement.setInt(5,0);
                            statement.registerOutParameter(6, Types.INTEGER);
                            statement.execute();
                            _attemptId=statement.getInt(6);
                        }
                        catch (Exception e)
                        {}
                        if(totalQues<totalQuestions)
                            totalQuestions=totalQues;

                        show("There will be "+totalQuestions+ " questions shown on the screen");
                        int difficultyLevel=1;
                        int questionNo=0;
                        int newDifficultyLevel=0;
                        HashSet<String> rootQuestions= new HashSet<String>();
                        HashSet<Integer> questionsAdded= new HashSet<Integer>();
                        int score=0;
                        int[] standardQuestions = new int[1000];
                        try {
                            OracleCallableStatement statement;
                            String sp = "{call SP_GET_EXID_QID_MAPPING" + " (?,?)}";
                            statement = (OracleCallableStatement) connection.prepareCall(sp);
                            statement.setInt(1, _hwSelected);
                            statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
                            statement.execute();
                            ResultSet rs = statement.getCursor(2);
                            int i = 0;

                            if (rs != null) {
                                while (rs.next()) {
                                    standardQuestions[i]=rs.getInt(1);
                                    i++;
                                }
                            }
                        }
                        catch (Exception e)
                        {}


                        for(int i=0;i<totalQuestions;i++)
                        {
                            boolean alreadyPresent=false;
                            questionNo = (int)(Math.random()*totalQues);
                            newDifficultyLevel = qDifficultyLevel[questionNo];
                            if(rootQuestions.contains(qrootText[questionNo])&& qrootText[questionNo] !=null)
                                alreadyPresent=true;
                            if(hwType.equalsIgnoreCase("A"))
                            {
                                while(newDifficultyLevel<difficultyLevel || alreadyPresent)
                                {
                                        alreadyPresent = false;
                                        questionNo = (int)(Math.random()*totalQues)+1;
                                        newDifficultyLevel = qDifficultyLevel[questionNo];
                                        if ((rootQuestions.contains(qrootText[questionNo]) && qrootText[questionNo] != null) || questionsAdded.contains(qId[questionNo]) )
                                            alreadyPresent = true;
                                }

                            }
                            else
                            {
                                int _qid= standardQuestions[i];
                                for(int j=0;j<totalQues;j++)
                                {
                                    if(qId[j]==_qid) {
                                        questionNo = j;
                                        break;
                                    }
                                }
                            }


                            rootQuestions.add(qrootText[questionNo]);
                            questionsAdded.add(qId[questionNo]);
                            show(question[questionNo]);
                            show("Please select from one of the 4 options:");
                            int k=1;
                            HashMap<Integer,Integer> answersMap= new HashMap<Integer, Integer>();
                            for(int j=0;j<qNo.length;j++)
                            {
                                if(qId[j]==qId[questionNo])
                                {
                                    show(k+": "+qAnswers[j]);
                                    answersMap.put(k,j);
                                    k++;
                                }
                            }
                            show("Enter the option number: ");
                            input=getInt();
                            if(qIsCorrect[(int)answersMap.get(input)]==1)
                            {
                                difficultyLevel++;
                                score+=correctPts;
                            }
                            else
                            {
                                score-=penalty;
                            }

                            try {
                                OracleCallableStatement statement;
                                String sp = "{call SP_SAVE_EACH_QUESTION_ATTEMPT" + " (?,?,?,?)}";
                                statement = (OracleCallableStatement) connection.prepareCall(sp);
                                statement.setInt(1, _attemptId);
                                statement.setString(3,qAnswers[answersMap.get(input)]);
                                statement.setInt(4,qId[questionNo]);
                                statement.setString(2,userId);
                                statement.execute();
                            }
                            catch (Exception e)
                            {}
                        }
                        try {
                            OracleCallableStatement statement;
                            String sp = "{call SP_SET_ATTEMPT" + " (?,?,?,?,?,?)}";
                            statement = (OracleCallableStatement) connection.prepareCall(sp);
                            statement.setString(1, _courseId);
                            statement.setInt(2,_hwSelected);
                            statement.setInt(3,_attemptId);
                            statement.setString(4,userId);
                            statement.setInt(5,score);
                            statement.registerOutParameter(6, Types.INTEGER);
                            statement.execute();
                            _attemptId=statement.getInt(6);
                        }
                        catch (Exception e)
                        {}


                        try {
                            OracleCallableStatement statement;
                            String sp = "{call SP_GET_SCORE_SCORING_POLICY" + " (?,?,?,?,?)}";
                            statement = (OracleCallableStatement) connection.prepareCall(sp);
                            statement.setString(1, userId);
                            show(userId);
                            statement.setInt(3,_hwSelected);
                            show(_hwSelected+"");
                            statement.setString(2,_courseId);
                            show(_courseId);
                            statement.registerOutParameter(4, Types.VARCHAR);
                            statement.registerOutParameter(5, Types.INTEGER);
                            statement.execute();
                        }
                        catch (Exception e)
                        {}



                    }

                }
            }
            else if(input==2)
            {
                try {
                    OracleCallableStatement statement;
                    String sp = "{call SP_GETHWS" + " (?,?)}";
                    statement = (OracleCallableStatement) connection.prepareCall(sp);
                    statement.setString(1, _courseId);
                    statement.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
                    statement.execute();
                    ResultSet rs = statement.getCursor(2);

                    int i = 1;
                    String hwName = "";
                    int hwId =0;
                    if (rs != null) {
                        while (rs.next()) {
                            hwId = rs.getInt(1);
                            hwName = rs.getString(2);
                            homeworks.put(i, hwId);
                            show(i + " : " + hwName);
                            i++;
                        }
                    }
                }
                catch (Exception e)
                {}
                show("Enter HW number to view homework attempts");
                show("Enter 0 to go back");
                input=getInt();
                if(input==0)
                    showHomeMenu();
                else
                {
                    _homeworkId=homeworks.get(input);

                    try {
                        OracleCallableStatement statement;
                        String sp = "{call SP_GETATTEMPTS" + " (?,?,?,?)}";
                        statement = (OracleCallableStatement) connection.prepareCall(sp);
                        statement.setString(1, userId);
                        statement.setString(2, _courseId);
                        statement.setInt(3, _homeworkId);
                        statement.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
                        statement.execute();
                        ResultSet rs = statement.getCursor(4);

                        int i = 1;
                        String date="";
                        int score=0;
                        if (rs != null) {
                            show("choice : attemptId : date  :  score");
                            while (rs.next()) {
                                _attemptId = rs.getInt(1);
                                date=rs.getString(2);
                                score = rs.getInt(3);
                                attempts.put(i, _attemptId);
                                show(i + " : " + _attemptId +" : "+date+"  :  "+score);
                                i++;
                            }
                        }
                    }
                    catch (Exception e)
                    {}

                    show("Enter attempt number to view attempt details");
                    show("Enter 0 to go back");
                    input=getInt();
                    if(input==0)
                        showHomeMenu();
                    else
                    {
                        _attemptId=attempts.get(input);
                        try {
                            OracleCallableStatement statement;
                            String sp = "{call SP_GETATTEMPTDETAILS" + " (?,?,?)}";
                            statement = (OracleCallableStatement) connection.prepareCall(sp);
                            statement.setString(1, userId);
                            statement.setInt(2, _attemptId);
                            show(_attemptId+"");
                            statement.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
                            statement.execute();
                            ResultSet rs = statement.getCursor(3);

                            int i = 1;
                            if (rs != null) {
                                while (rs.next()) {
                                    show(i+") "+rs.getString(2)+ "  ::Given Answer: "+ rs.getString(3) +"  :: Correct Ans/Hint: "+rs.getString(4)+ ":: Solution: "+rs.getString(5));
                                    i++;
                                }
                            }
                        }
                        catch (Exception e)
                        {}
                    }
                }
            }
            else
                showHomeMenu();
        }
        showHomeMenu();

    }

    public void show(String statement)
    {
        System.out.println(statement);
    }
    public String getStr()
    {
        // Reading from System.in
        String str = reader.nextLine(); // Scans the next token of the input as an int.
        return str;
    }
    public int getInt()
    { // Reading from System.in
        String inputString="";
        int input=-1;
        while (input==-1)
        {
            try
            {
                inputString = reader.nextLine();
                input=Integer.parseInt(inputString);
            }
            catch (Exception e)
            {
            }
        } // Scans the next token of the input as an int.
        return input;
    }
    public final  void clearScreen()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
