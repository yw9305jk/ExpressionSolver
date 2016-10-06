// Kou Vang
// ICS 240.1
// Assignment 6

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class ExpressionSolver extends JFrame{

	  /**
	   * Declare and ready the variables for the GUI
	   **/   
	eventInFixSolve i = new eventInFixSolve();
	eventPostFixSolve p = new eventPostFixSolve();
	
	private JLabel inFixLabel;
	private JLabel postFixLabel;
	private JLabel resultLabel;
	
	
	private JButton solveInFixButton;
	private JButton solvePostFixButton;

	
	private JTextField inFixTextfield;
	private JTextField postFixTextfield;
	private JTextField resultTextfield;
	
	  /**
	   * Initialize a GUI for use of the program
	   * @param - none
	   **/   
	public ExpressionSolver ()
	{
		setLayout(new GridLayout(0,2));
		
		inFixLabel = new JLabel("In-Fix Expression (Ex: a + b * c (include spaces))", JLabel.CENTER);
		add(inFixLabel);
		inFixTextfield = new JTextField(15);
		inFixTextfield.setHorizontalAlignment(JLabel.CENTER);
		add(inFixTextfield);
		
		postFixLabel = new JLabel("Post-Fix Expression (Ex: a b c * + (include spaces))", JLabel.CENTER);
		add(postFixLabel);
		postFixTextfield = new JTextField(15);
		postFixTextfield.setHorizontalAlignment(JLabel.CENTER);
		add(postFixTextfield);
		
		solveInFixButton = new JButton("Solve In-Fix Expression");
		add(solveInFixButton);
		solveInFixButton.addActionListener(i);
		solvePostFixButton = new JButton("Solve Post-Fix Expression");
		add(solvePostFixButton);
		solvePostFixButton.addActionListener(p);
		
		resultLabel = new JLabel("Result", JLabel.CENTER);
		add(resultLabel);
		resultTextfield = new JTextField(15);
		resultTextfield.setHorizontalAlignment(JLabel.CENTER);
		resultTextfield.setEnabled(false);
		add(resultTextfield);
		
	}
	
	  /**
	   * Action listener for the button to convert Infix into Postfix
	   * @param - none
	   **/   
	public class eventInFixSolve implements ActionListener {
		public void actionPerformed(ActionEvent i)
		{
			resultTextfield.setText(infixToPostfixConvert(inFixTextfield.getText()));
		}
	}
		
	  /**
	   * Action listener for the button to convert Postfix into Infix
	   * @param - none
	   **/  
	public class eventPostFixSolve implements ActionListener {
		public void actionPerformed(ActionEvent p)
		{
			resultTextfield.setText(postFixToInfixConvert(postFixTextfield.getText()));
			//PostfixToInfixConverter conv = new PostfixToInfixConverter();
			//String strInfix = conv.Convert("abc*+");
			//resultTextfield.setText(strInfix);
		}
	}
	
	  /**
	   * Method to read string from a buffer
	   * @param - none
	   **/  
	public static String ReadString()
    {
         try
         {
               InputStreamReader input = new InputStreamReader(System.in);
               BufferedReader reader = new BufferedReader(input);
               return reader.readLine();
         }

         catch (Exception e)
         {
               e.printStackTrace();
               return "";
         }

    }

	  /**
	   * Method to read integers from a buffer
	   * @param - none
	   **/  
    public static int ReadInteger()

    {
         try
         {
               InputStreamReader input = new InputStreamReader(System.in);
               BufferedReader reader = new BufferedReader(input);
               return Integer.parseInt(reader.readLine());
         }

         catch (Exception e)
         {
               e.printStackTrace();
               return 0;
         }
    }

   
    /**
	   * Method to convert an Infix sequence into Postfix
	   * @param - infixBuffer
	   * The string that will be passed into and then converted into Postfix
	   * @precondition - Must be a valid form of Infix in order to operate correctly
	   * @postcondition - Infix sequence will be returned back as a string but in Postfix sequence
	   **/  
    public static String infixToPostfixConvert(String infixBuffer)

    {

       int priority = 0;
       String postfixBuffer = "";
       Stack s1 = new Stack();

       for (int i = 0; i < infixBuffer.length(); i++)

       {
          char ch = infixBuffer.charAt(i);
          if (ch == '+' || ch == '-' || ch == '*' || ch == '/')
          {
             // check the precedence
             if (s1.size() <= 0)
                s1.push(ch);
             else
             {
                Character chTop = (Character) s1.peek();
                if (chTop == '*' || chTop == '/')
                   priority = 1;
                
                else
                   priority = 0;
                
                if (priority == 1)
                {
                   if (ch == '+' || ch == '-')
                   {
                      postfixBuffer += s1.pop();
                      i--;
                   }
                   else
                   { // Same
                      postfixBuffer += s1.pop();
                      i--;
                   }
                }

                else
                {
                   if (ch == '+' || ch == '-')
                   {
                      postfixBuffer += s1.pop();
                      s1.push(ch);
                   }

                   else
                      s1.push(ch);

                }
             }
          }

          else
          {
             postfixBuffer += ch;
          }

       }

       int len = s1.size();

       for (int j = 0; j < len; j++)
          postfixBuffer += s1.pop();
       
       return postfixBuffer;

    }
 
    /**
	   * Method to convert a Postfix sequence into Infix
	   * @param - strInfixExp
	   * The string that will be passed into and then converted into Infix
	   * @precondition - Must be a valid form of Postfix in order to operate correctly
	   * @postcondition - Postfix sequence will be returned back as a string but in Infix sequence
	   **/ 
    String postFixToInfixConvert(String strInfixExp)
    {
    	   Stack<String> _stack;
    	    _stack = new Stack<String>();
    	    
    	    String[] strArgs = strInfixExp.split(" ");
    
    	    	for (int i = 0; i < strArgs.length; i++)
    	    	{
    	    		String str = strArgs[i];
    
    	    		if (!IsOperator(str))
    	    			_stack.push(str);
    
    	    		else
    	    		{
    	    			String arg2 = _stack.pop();
    	    			String arg1 = _stack.pop();
    	    			_stack.push(arg1 + str + arg2);
    	    		}
    	    		
    	    	}
    
    	    	String strInfix = "";
    	    	for (int i = 0; i < _stack.size(); i++)
    	    	{
    	    		strInfix = strInfix + _stack.pop();
    	    	}
    
    	    	return strInfix;
    
    	}

    /**
	   * Method to return the operator
	   * @param - str
	   * str is the String that will be passed in for the operator
	   **/ 
    private boolean IsOperator(String str)
    	{
    	return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    	}

    
	public static void main(String[] args) {
		
		/**
		   * Create and initialize a gui that will operate the program
		   **/ 
		ExpressionSolver gui = new ExpressionSolver();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(800, 600);
		gui.setVisible(true);
		gui.setTitle("In-Fix / Post-Fix Solver");
		
		String infixBuffer = "";
        String postfixBuffer = "";

       
        /**
  	   * Testers for the methods
  	   **/ 
        if(args.length == 1)

        {

           infixBuffer = args[0];
           postfixBuffer = infixToPostfixConvert(infixBuffer);
           System.out.println("InFix  :\t" + infixBuffer);
           System.out.println("PostFix:\t" + postfixBuffer);
           System.out.println();

        }

        else

        {

           infixBuffer = "a+b*c";
           postfixBuffer = infixToPostfixConvert(infixBuffer);
           System.out.println("InFix  :\t" + infixBuffer);
           System.out.println("PostFix:\t" + postfixBuffer);
           System.out.println();

          
           infixBuffer = "a+b*c/d-e";
           postfixBuffer = infixToPostfixConvert(infixBuffer);
           System.out.println("InFix  :\t" + infixBuffer);
           System.out.println("PostFix:\t" + postfixBuffer);
           System.out.println();

          
           infixBuffer = "a+b*c/d-e+f*h/i+j-k";
           postfixBuffer = infixToPostfixConvert(infixBuffer);
           System.out.println("InFix  :\t" + infixBuffer);
           System.out.println("PostFix:\t" + postfixBuffer);
           System.out.println();

           
        }

     }

}


