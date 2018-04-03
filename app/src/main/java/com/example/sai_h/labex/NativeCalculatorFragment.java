package com.example.sai_h.labex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class NativeCalculatorFragment extends Fragment implements View.OnClickListener{
    View v;
    float val1=0,val2=0;
    String op;
    EditText e;
    Button[] b;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*ViewGroup to be inflated into the activity
          This is not necessary in activity. In activity it is autogenerated
          super.onCreate(savedInstanceState);
          setContentView(R.layout.your_activity);
         */
        v = inflater.inflate(R.layout.fragment_calculator,container,false);
        return v;//Returns the Viewgroup to the activity class for inflation
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //All the view elements of the fragment are identified using the viewgroup. Here viewgroup is stored in variable v
        //InCase of activity this is not necessary. Eg: Button b = findViewById(R.id.BTN);
        b = new Button[16];
        e = (EditText)v.findViewById(R.id.val);
        b[0] = (Button)v.findViewById(R.id.b1);
        b[1] = (Button)v.findViewById(R.id.b2);
        b[2] = (Button)v.findViewById(R.id.b3);
        b[3] = (Button)v.findViewById(R.id.b4);
        b[4] = (Button)v.findViewById(R.id.b5);
        b[5] = (Button)v.findViewById(R.id.b6);
        b[6] = (Button)v.findViewById(R.id.b7);
        b[7] = (Button)v.findViewById(R.id.b8);
        b[8] = (Button)v.findViewById(R.id.b9);
        b[9] = (Button)v.findViewById(R.id.b0);
        b[10] = (Button)v.findViewById(R.id.eq);
        b[11] = (Button)v.findViewById(R.id.c);
        b[12] = (Button)v.findViewById(R.id.add);
        b[13] = (Button)v.findViewById(R.id.sub);
        b[14] = (Button)v.findViewById(R.id.mul);
        b[15] = (Button)v.findViewById(R.id.div);
        for(int i=0;i<16;i++){
            //Set onClick listener for each button
            b[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            //To erase the text in EditText.
            case R.id.c: e.setText("");
                         val1=0;
                         val2=0;
                         break;
            case R.id.b1:
            case R.id.b2:
            case R.id.b3:
            case R.id.b4:
            case R.id.b5:
            case R.id.b6:
            case R.id.b7:
            case R.id.b8:
            case R.id.b9:
            case R.id.b0:// For digits 0-9 the corresponding number of button clicked is appended on the EditText
                         Button b = (Button)v.findViewById(view.getId());
                         e.setText(e.getText()+b.getText().toString());
                         break;
            case R.id.add:
            case R.id.sub:
            case R.id.mul:
            case R.id.div://The operation is identified here
                          val1 = Float.parseFloat(e.getText().toString());
                          e.setText("");
                          Button b1 = (Button)v.findViewById(view.getId());
                          op = (String) b1.getText();
                          break;
            case R.id.eq: val2 =//Operation performed and result appended on EditText after '=' is pressed
                                Float.parseFloat(e.getText().toString());
                                switch(op){
                                case "+": val1 = val1+val2;
                                          break;
                                case "-": val1 = val1-val2;
                                    break;
                                case "*": val1 = val1*val2;
                                    break;
                                case "/": val1 = val1/val2;
                                    break;
                                }
                                e.setText(String.valueOf(val1));
                                val2=0;
                                op="";


        }
    }
}
