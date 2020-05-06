package com.example.threadpractice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    var board = Array(3){Array(3){" "}}
    var Player = "X"
    var opponent = "O"
    var ActivePlayer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        }

    fun buclick(view : View)
        {
            val buSelected = view as Button
            var cellID = 0
            when(buSelected.id)
            {
                R.id.bu1->cellID=1
                R.id.bu2->cellID=2
                R.id.bu3->cellID=3
                R.id.bu4->cellID=4
                R.id.bu5->cellID=5
                R.id.bu6->cellID=6
                R.id.bu7->cellID=7
                R.id.bu8->cellID=8
                R.id.bu9->cellID=9
            }

            var row=-1;
            var col=-1
            if(cellID>0 && cellID<4)
            {
                row = 0
            }else if(cellID>3 && cellID<7)
            {
                row = 1
            }else
            {
                row = 2
            }
            if(cellID%3==0)
            {
                col = 2
            }else
            {
                col = cellID%3-1
            }
            //Toast.makeText(this," "+row+" "+col,Toast.LENGTH_LONG).show()
            board[row][col]=Player
            PlayGame(cellID,buSelected)
        }

            fun PlayGame(cellId: Int, buSelected:Button)
            {
                if(ActivePlayer == 1)
                {
                    buSelected.text = "X"
                    buSelected.isEnabled = false
                    buSelected.setBackgroundResource(R.color.blue)
                    ActivePlayer = 2
                    if(CheckWinner()==false)
                    {
                        AutoPlay()
                    }

                }else
                {
                    buSelected.text = "O"
                    buSelected.isEnabled = false
                    buSelected.setBackgroundResource(R.color.darkgreen)
                    ActivePlayer = 1
                    CheckWinner()
                }
                 }

            fun CheckWinner():Boolean
            {
                for(i in 0..2)
                {
                    if(board[i][0]==board[i][1] && board[i][1]==board[i][2])
                    {
                        if(board[i][0]==Player)
                        {
                            Toast.makeText(this,"Player Won the Game",Toast.LENGTH_LONG).show()
                            return true
                        }else if(board[i][0]==opponent)
                        {
                            Toast.makeText(this,"Computer Won the Game",Toast.LENGTH_LONG).show()
                            return true
                        }
                    }
                }

                for(i in 0..2)
                {
                    if(board[0][i]==board[1][i] && board[1][i]==board[2][i])
                    {
                        if(board[0][i]==Player)
                        {
                            Toast.makeText(this,"Player Won the Game",Toast.LENGTH_LONG).show()
                            return true
                        }else if(board[0][i]==opponent)
                        {
                            Toast.makeText(this,"Computer Won the Game",Toast.LENGTH_LONG).show()
                            return true
                        }
                    }
                }

                if(board[0][0]==board[1][1] && board[1][1]==board[2][2])
                {
                    if(board[0][0]==Player)
                    {
                        Toast.makeText(this,"Player Won the Game",Toast.LENGTH_LONG).show()
                        return true
                    }else if(board[0][0]==opponent)
                    {
                        Toast.makeText(this,"Computer Won the Game",Toast.LENGTH_LONG).show()
                        return true
                    }
                }

                if(board[0][2]==board[1][1] && board[1][1]==board[2][0])
                {
                    if(board[1][1]==Player)
                    {
                        Toast.makeText(this,"Player Won the Game",Toast.LENGTH_LONG).show()
                        return true
                    }else if(board[1][1]==opponent)
                    {
                        Toast.makeText(this,"Computer Won the Game",Toast.LENGTH_LONG).show()
                        return true
                    }
                }
                return false
            }

        fun evaluate():Int
        {
            for(i in 0..2)
            {
                if(board[i][0]==board[i][1] && board[i][1]==board[i][2])
                {
                    if(board[i][0]==Player)
                    {
                        return 10
                    }else if(board[i][0]==opponent)
                    {
                        return -10
                    }
                }
            }

            for(i in 0..2)
            {
                if(board[0][i]==board[1][i] && board[1][i]==board[2][i])
                {
                    if(board[0][i]==Player)
                    {
                        return 10
                    }else if(board[0][i]==opponent)
                    {
                        return -10
                    }
                }
            }

            if(board[0][0]==board[1][1] && board[1][1]==board[2][2])
            {
                if(board[0][0]==Player)
                {
                    return 10
                }else if(board[0][0]==opponent)
                {
                    return -10
                }
            }

            if(board[0][2]==board[1][1] && board[1][1]==board[2][0])
            {
                if(board[0][2]==Player)
                {
                    return 10
                }else if(board[0][2]==opponent)
                {
                    return -10
                }
            }
            return 0
        }

        fun isMovesLeft():Boolean{
            for(i in 0..2)
            {
                for(j in 0..2)
                {
                    if(board[i][j]==" ")
                    {
                        return true
                    }
                }
            }
            return false
        }
        fun minimax(depth:Int,isMax:Boolean):Int
        {
            val score = evaluate()
            if(score==10)
            {
                return score-depth
            }
            if(score==-10)
            {
                return score+depth
            }

            if(isMovesLeft()==false)
            {
                return 0
            }
            if(isMax)
            {
                var best = -1000
                for(i in 0..2)
                {
                    for(j in 0..2)
                    {
                        if(board[i][j]==" ")
                        {
                            board[i][j]= Player
                            best = max(best-depth,minimax(depth+1,!isMax))
                            board[i][j]=" "
                        }
                    }
                }
                return best
            }else
            {
                var best = 1000
                for(i in 0..2)
                {
                    for(j in 0..2)
                    {
                        if(board[i][j]==" ")
                        {
                            board[i][j]=opponent
                            best = min(best+depth,minimax(depth+1,!isMax))
                            board[i][j]=" "
                        }
                    }
                }
                return best
            }

        }
        fun AutoPlay()
        {
            var row=-1
            var col=-1
            var bestVal=-1000
            for(i in 0..2)
            {
                for(j in 0..2) {
                    if (board[i][j] == " ") {
                        board[i][j] = opponent
                        var moveVal = minimax(0, false)
                        board[i][j] = " "
                        if (moveVal > bestVal) {
                            row = i
                            col = j
                            bestVal = moveVal
                        }
                    }

                }
            }
            Toast.makeText(this," " + bestVal,Toast.LENGTH_SHORT).show()
            val cellID = 3*(row)+col+1
            val buSelect:Button?
            when(cellID)
            {
                1->buSelect=bu1
                2->buSelect=bu2
                3->buSelect=bu3
                4->buSelect=bu4
                5->buSelect=bu5
                6->buSelect=bu6
                7->buSelect=bu7
                8->buSelect=bu8
                9->buSelect=bu9
                else->{
                    buSelect = bu1
                }
            }
            PlayGame(cellID,buSelect)
        }

}

