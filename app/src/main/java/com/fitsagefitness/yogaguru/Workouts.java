package com.fitsagefitness.yogaguru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class Workouts extends AppCompatActivity {

        String level;
        String program,stress;
        // Array of strings for ListView Title
        String[] listviewTitle ;
        int[] listviewImage;
        String[] listviewShortDescription;
        String [] youtubeVideos;
        boolean isStress = false;

        int max;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_workouts);
            max = 8;
            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
            level = getIntent().getStringExtra("Level");
            program = getIntent().getStringExtra("Program");
            setTitle(program);
            stress = getIntent().getStringExtra("Stress");
            program(program);






            if(MainActivity.mInterstitialAd!=null && MainActivity.mInterstitialAd.isAdLoaded() && !MainActivity.isAdFree){

                MainActivity.mInterstitialAd.show();
            }

            if(stress!=null)
            {
                max = 6;
                isStress = true;
            }

            else if(level.equals("Intermediate"))
            {
                max = listviewTitle.length - 2;
            }

            else if(level.equals("Advance") )
            {
                max = listviewTitle.length;
            }




            for (int i = 0; i <=max; i++) {
                if(i==max) {

                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("listview_title", "Corpse Pose");
                    hm.put("listview_discription", "Shavasana");
                    hm.put("listview_image", String.valueOf(R.drawable.shavasna));
                    aList.add(hm);


                }
                else
                {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("listview_title", listviewTitle[i]);
                    hm.put("listview_discription", listviewShortDescription[i]);
                    hm.put("listview_image", Integer.toString(listviewImage[i]));
                    aList.add(hm);

                }
            }

            String[] from = {"listview_image", "listview_title", "listview_discription"};
            int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
            ListView androidListView = (ListView) findViewById(R.id.list_view);
            androidListView.setAdapter(simpleAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                    // Getting the Container Layout of the ListView
                    LinearLayout linearLayoutParent = (LinearLayout) container;

                    // Getting the inner Linear Layout
                    LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);

                    // Getting the Country TextView
                    //TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);
                    Bundle b=new Bundle();

                    b.putStringArray("youtube", youtubeVideos);
                    b.putInt("position", position);
                    b.putIntArray("image", listviewImage);
                    b.putStringArray("asanaName", listviewTitle);
                    b.putInt("max", max);
                    b.putString("Level", level );
                    b.putBoolean("isStress", isStress);
                    Intent intent = new Intent(Workouts.this, FullScreenWorkout.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    //Toast.makeText(getBaseContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            };

            // Setting the item click listener for the listview
            androidListView.setOnItemClickListener(itemClickListener);
        }

        public void program(String program)
        {
            switch (program) {
                case "Weight Loss":
                    listviewTitle = new String[]{
                            "Low Lunge Variation", "Boat Pose", "Tree Pose", "Wide-Legged Forward Bend",
                            "Plank Pose", "Revolved Triangle Pose", "Chair Pose", "Warrior Pose 1",
                            "Warrior Pose 2", "Warrior Pose 3", "Shoulder Stand", "Lord of the Dance Pose",
                    };

                    listviewImage = new int[]{
                            R.drawable.anjanyasna, R.drawable.fullboat, R.drawable.treepose, R.drawable.widelegforwardbend,
                            R.drawable.plank, R.drawable.pavritaananjanyasa, R.drawable.chairpose, R.drawable.warrior1,
                            R.drawable.warrior2, R.drawable.warrior3, R.drawable.shouldetstand, R.drawable.natrajasan,
                    };


                    listviewShortDescription = new String[]{
                            "Anjaneyasana", "Navasana", "Vrikshasana", "Prasarita Padottanasana",
                            "Kumbhakasana", "Pavritta Trikonasana", "Utkatasana", "Virabhadrasana 1",
                            "Virabhadrasana 2", "Virabhadrasana 3", "Sarvangasana", "Natarajasana",
                    };

                    youtubeVideos = new String[]{
                            "zg-X7uLDGqU", "zJnfHKneZkc", "-Lct-3GctVE", "US_6kS-iCo4",
                            "WphcbGWzfRk", "s8aOvs5cUuQ", "JyVNFNH-oVA", "i1PD4cj69oQ",
                            "NqksT4ymk0g", "eJMXMAhnN8o", "s7NsMrZxvbI", "Vf9ZlDBBLHc",
                    };

                    break;

                case "Flexibility and Strength":
                    listviewTitle = new String[]{
                            "Downward-Facing Dog", "Low Lunge Variation", "Revolved Triangle Pose",
                            "Butterfly Pose", "Supine Abdominal Twist", "Seated Forward Bend", "Standing Forward Bend",
                            "Wide-Legged Forward Bend", "Pigeon Pose", "Child's Pose", "Upward-Facing Dog", "Sun Salutation",
                    };

                    listviewImage = new int[]{
                            R.drawable.halfboat, R.drawable.anjanyasna, R.drawable.pavritaananjanyasa,
                            R.drawable.butterfly, R.drawable.abtwist, R.drawable.seatedforward, R.drawable.forwardbend,
                            R.drawable.widelegforwardbend, R.drawable.pigeon, R.drawable.childpose, R.drawable.upwarddog, R.drawable.surya
                    };


                    listviewShortDescription = new String[]{
                            "Adho Mukha Svanasana", "Anjaneyasana", "Pavritta Trikonasana",
                            "Baddha Konasana","Jathara Parivritti", "Paschimottanasana", "Uttanasana",
                            "Prasarita Padottanasana", "Kapotasana", "Balasana", "Urdhva Mukha Svanasana","Surya Namaskar",
                    };

                    youtubeVideos = new String[]{
                            "tI4Vvb_d7KM", "zg-X7uLDGqU", "-Lct-3GctVE",
                            "Jti4wd4AHx4", "s8aOvs5cUuQ", "ms6pUOLYhqw", "imF1wKB-kMA",
                            "US_6kS-iCo4", "9FBwBIJcYao", "NIXkfoniaWM", "a5g7NzH0NHk","2RMHRyuD03k",
                    };

                    break;


                case "Stress and Anxiety":
                    listviewTitle = new String[]{
                            "Standing Forward Bend","Lion's Breath Pose", "Child's Pose", "Wind-Relieving Pose",
                            "Butterfly Pose", "Easy Pose",
                    };

                    listviewImage = new int[]{
                            R.drawable.forwardbend, R.drawable.thunder, R.drawable.childpose,R.drawable.wind,
                            R.drawable.butterfly,R.drawable.easy,
                    };


                    listviewShortDescription = new String[]{
                            "Uttanasana", "Simhasana","Balasana", "Pavanamuktasana",
                            "Baddha Konasana", "Sukhasana",
                    };
                    youtubeVideos = new String[]
                            {"imF1wKB-kMA", "GbSr8RY_sp4", "NIXkfoniaWM","ZzTrddYD1RU",
                             "Jti4wd4AHx4", "N-Jvzivik1I",
                            };
                    break;


                case "Weight Gain":
                    listviewTitle = new String[]{
                            "Cobra Pose", "Thunderbolt pose", "Wind-Relieving Pose", "Fish Pose",
                            "Shoulder Stand", "Bow Pose", "Chair Pose", "Warrior Pose 1",
                            "Warrior Pose 2", "Warrior Pose 3", "Sun Salutation",
                    };

                    listviewImage = new int[]{
                            R.drawable.cobra, R.drawable.thunder, R.drawable.wind, R.drawable.fish,
                            R.drawable.shouldetstand, R.drawable.bow, R.drawable.chairpose, R.drawable.warrior1,
                            R.drawable.warrior2, R.drawable.warrior3, R.drawable.surya,
                    };


                    listviewShortDescription = new String[]{
                            "Bhujangasana", "Vajrasana", "Pavanamuktasana", "Matsyasana",
                            "Sarvangasana", "Dhanurasana", "Utkatasana","Virabhadrasana 1",
                            "Virabhadrasana 2", "Virabhadrasana 3", "Surya Namaskar",
                    };

                    youtubeVideos = new String[]{
                            "Pek6Zq8VmBQ", "ncYm6eqa1NA", "ZzTrddYD1RU", "Ds3S5KCGZFs",
                            "s7NsMrZxvbI","vVPHZXEAiLg","JyVNFNH-oVA", "i1PD4cj69oQ",
                            "NqksT4ymk0g", "eJMXMAhnN8o","2RMHRyuD03k"

                    };

                    break;



                case "Toned Body":
                    listviewTitle = new String[]{
                            "Child's Pose", "Downward-Facing Dog", "Plank Pose", "Four-Limbed Staff Pose",
                            "Upward-Facing Dog", "Warrior Pose 1", "Warrior Pose 2", "Warrior Pose 3",
                            "Tree Pose", "Full Boat Pose","Sun Salutation",
                    };

                    listviewImage = new int[]{
                            R.drawable.childpose, R.drawable.downwarddog, R.drawable.plank, R.drawable.chatur,
                            R.drawable.upwarddog, R.drawable.warrior1, R.drawable.warrior2, R.drawable.warrior3,
                            R.drawable.treepose, R.drawable.fullboat, R.drawable.surya,
                    };


                    listviewShortDescription = new String[]{
                            "Balasana", "Adho Mukha Svanasana", "Kumbhakasana", "Chaturanga Dandasana",
                            "Urdhva Mukha Svanasana", "Virabhadrasana 1", "Virabhadrasana 2", "Virabhadrasana 3",
                            "Vrikshasana", "Paripurna Navasana", "Surya Namaskar",
                    };
                    youtubeVideos = new String[]{
                            "NIXkfoniaWM", "tI4Vvb_d7KM", "WphcbGWzfRk", "Nc8YE-4jlos",
                            "a5g7NzH0NHk","i1PD4cj69oQ", "NqksT4ymk0g", "eJMXMAhnN8o",
                            "-Lct-3GctVE", "zJnfHKneZkc","2RMHRyuD03k",

                    };

                    break;

                case "Fitness and Endurance":
                    listviewTitle = new String[]{
                            "Sun Salutation", "Wide-Legged Forward Bend", "Fish Pose", "Low Lunge Variation",
                            "Plank Pose", "Staff Pose", "Head-to-Knee Pose", "Wind-Relieving Pose",
                            "Warrior Pose 2","Warrior Pose 3", "Lord of the Dance Pose",
                    };

                    listviewImage = new int[]{
                            R.drawable.surya, R.drawable.widelegforwardbend, R.drawable.fish, R.drawable.anjanyasna,
                            R.drawable.plank, R.drawable.satff, R.drawable.seatedforward, R.drawable.wind,
                            R.drawable.warrior2,R.drawable.warrior3, R.drawable.natrajasan,
                    };


                    listviewShortDescription = new String[]{
                            "Surya Namaskar", "Prasarita Padottanasana", "Matsyasana", "Anjaneyasana",
                            "Kumbhakasana", "Dandasana", "Janusirsasana", "Pavanamuktasana", "Virabhadrasana 2",
                            "Virabhadrasana 3",  "Natarajasana",
                    };

                    youtubeVideos = new String[]{
                            "2RMHRyuD03k", "US_6kS-iCo4", "Ds3S5KCGZFs", "zg-X7uLDGqU",
                            "WphcbGWzfRk","PHjqMK8ayd0","ms6pUOLYhqw","ZzTrddYD1RU",
                            "NqksT4ymk0g","eJMXMAhnN8o","Vf9ZlDBBLHc",
                    };
                    break;
            }

        }
    }


