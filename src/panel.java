import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

public class panel extends JPanel implements ActionListener
{
    //width and height of the snake game
    static int width=1200;
    static int height=600;
    //unit is the size of each box(cell) of the board.
    static int unit=50;
    int totalUnits=(width*height)/unit;
    int score;
    //x and y coordinates of the food
    int fx,fy;
    //length of the snake
    int length=3;
    char direction='R';

    int count=0;
    boolean flag=false;
    boolean treasure=false;

    //created for placing the food randomly on the x and y-axis.
    Random random;

    Timer timer;
    static int delay=160;
    int[] xsnake=new int[totalUnits];

    int ysnake[]=new int[totalUnits];


    panel()
    {
        //size of the application
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.DARK_GRAY);
        //key action listener
        this.addKeyListener(new MyKey());
        //this is enabled so that the application can take input when
        //it is running.
        this.setFocusable(true);
        random = new Random();

        gameStart();
    }
    public void gameStart()
    {
        flag=true;
        spawnFood();
        timer=new Timer(delay,this);
        timer.start();
    }
    public void spawnFood()
    {
            fx=random.nextInt((int)width/unit)*50;
            fy=random.nextInt((int)height/unit)*50;
    }

    //is implicitly called when a panel is created.
    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0,0,width,height);
        if(flag) {
            //to spawn the food particle
            if(count!=0 && count%5==0) {
                graphics.setColor(Color.red);
                graphics.fillOval(fx,fy,70,70);
                treasure=true;
            }
            else
            {
                graphics.setColor(Color.ORANGE);
                graphics.fillOval(fx, fy, 50, 50);
            }

            //to spawn the snake body
            for(int i=0;i<length;i++)
            {
                if(i==0)
                {
                    graphics.setColor(Color.RED);
                    graphics.fillRect(xsnake[0],ysnake[0],unit,unit);
                }
                else
                {
                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
            }
            graphics.setColor(Color.CYAN);
            graphics.setFont(new Font("Gadugi", Font.BOLD,40));
            FontMetrics fontMetrics= getFontMetrics(graphics.getFont());
            graphics.drawString("Score : "+score,(width- fontMetrics.stringWidth("Score : "+score))/2,graphics.getFont().getSize());
        }
        else {
            gameOver(graphics);
        }
    }
    public void move()
    {
        //for all other body parts
        for(int i=length;i>0;i--)
        {
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        //for updating the head
        switch (direction)
        {
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;
        }
    }
    void check()
    {
        //checking if the body and head are touched, game over or not
        for(int i=length;i>0;i--)
        {
            if(xsnake[i]==xsnake[0] && ysnake[i]==ysnake[0])
            {
                flag=false;
            }
        }
        //checking with tiles

        if(xsnake[0]>width)
        {
            flag=false;
        }
        else if(xsnake[0]<0)
        {
            flag=false;
        }
        else if(ysnake[0]>height)
        {
            flag=false;
        }
        else if(ysnake[0]<0)
        {
            flag=false;
        }

        if(flag==false) {
            gameOver(getGraphics());
            timer.stop();
        }
    }

    public void foodEaten()
    {
        if(xsnake[0]==fx && ysnake[0]==fy)
        {
            length++;
            count++;
            if(treasure==true)
            {
                score=score+20;
                treasure=false;
            }
            else {
                score=score+5;
            }
            spawnFood();
        }
    }
    public void gameOver(Graphics graphics)
    {

        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0,0,width,height);

        graphics.setColor(Color.ORANGE);
        graphics.setFont(new Font("Gadugi", Font.ITALIC,30));

        FontMetrics fontMetrics1= getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over",(width- fontMetrics1.stringWidth("Game Over"))/2,graphics.getFont().getSize());

        FontMetrics fontMetrics2= getFontMetrics(graphics.getFont());
        graphics.drawString("Press 'R' to Replay: ",(width- fontMetrics2.stringWidth("Press 'R' to Replay : "))/2,(height+200)/2);
        graphics.drawString("Press 'S' to Replay: ",(width- fontMetrics2.stringWidth("Press 'R' to Replay : "))/2,(height+300)/2);

        Scores scores=new Scores();
        int highestScore=scores.HighestScore();
        if(score>highestScore)
        {
            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font("Gadugi",Font.ITALIC,50));
            FontMetrics fontMetrics3= getFontMetrics(graphics.getFont());
            graphics.drawString("Congratulations... ",(width- fontMetrics3.stringWidth("Congratualation... "))/2,graphics.getFont().getSize()+50);
        }
        graphics.setColor(Color.BLACK);
        FontMetrics fontMetrics3= getFontMetrics(graphics.getFont());
        graphics.drawString("Highest Score : "+highestScore,(width- fontMetrics3.stringWidth("Highest Score : "+highestScore))/2,graphics.getFont().getSize()+150);

        graphics.setFont(new Font("Gadugi", Font.BOLD,30));
        FontMetrics fontMetrics= getFontMetrics(graphics.getFont());
        graphics.drawString("Your Score: "+score,(width- fontMetrics.stringWidth("Your Score: "))/2,(height-100)/2);


    }
    public class MyKey extends KeyAdapter
    {
        public void keyPressed(KeyEvent k)
        {
            switch ((k.getKeyCode()))
            {
                case VK_UP:
                    if(direction!='D')
                    {
                        direction='U';
                    }
                    break;
                case VK_DOWN:
                    if(direction!='U')
                    {
                        direction='D';
                    }
                    break;
                case VK_RIGHT:
                    if(direction!='L')
                    {
                        direction='R';
                    }
                    break;
                case VK_LEFT:
                    if(direction!='R')
                    {
                        direction='L';
                    }
                    break;
                case VK_R:
                    if(!flag)
                    {
                        score=0;
                        length=3;
                        direction='R';
                        count=0;
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        gameStart();
                    }
                    break;
                case VK_S:
                    savescore();
            }
        }
    }
    public void savescore()
    {
        Scores scores=new Scores();
        String name=(String) JOptionPane.showInputDialog(null,"Enter your name:");
        scores.ConnectionAndInsertion(name,score);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(flag==true)
        {
            move();
            foodEaten();
            check();
        }
        repaint();
    }

}
