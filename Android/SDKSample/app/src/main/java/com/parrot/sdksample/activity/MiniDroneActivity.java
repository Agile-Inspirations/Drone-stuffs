package com.parrot.sdksample.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARFrame;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.sdksample.R;
import com.parrot.sdksample.drone.MiniDrone;
import com.parrot.sdksample.view.H264VideoView;

import java.util.Random;

public class MiniDroneActivity extends AppCompatActivity {
    private static final String TAG = "MiniDroneActivity";
    private MiniDrone mMiniDrone;

    private ProgressDialog mConnectionProgressDialog;
    private ProgressDialog mDownloadProgressDialog;

    private H264VideoView mVideoView;

    private TextView mBatteryLabel;
    private Button mTakeOffLandBt;
    private Button mAnglieinc;
    private Button mBowtie;
    private Button mpuppy;
    private Button mgamble;
    private Button mSpiral;
    private Button insanity;
    private Button hit90s;

    private int mNbMaxDownload;
    private int mCurrentDownloadIndex;

    public int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minidrone);

        initIHM();

        Intent intent = getIntent();
        ARDiscoveryDeviceService service = intent.getParcelableExtra(DeviceListActivity.EXTRA_DEVICE_SERVICE);
        mMiniDrone = new MiniDrone(this, service);
        mMiniDrone.addListener(mMiniDroneListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // show a loading view while the minidrone is connecting
        if ((mMiniDrone != null) && !(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING.equals(mMiniDrone.getConnectionState())))
        {
            mConnectionProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
            mConnectionProgressDialog.setIndeterminate(true);
            mConnectionProgressDialog.setMessage("Connecting ...");
            mConnectionProgressDialog.setCancelable(false);
            mConnectionProgressDialog.show();

            // if the connection to the MiniDrone fails, finish the activity
            if (!mMiniDrone.connect()) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mMiniDrone != null)
        {
            mConnectionProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
            mConnectionProgressDialog.setIndeterminate(true);
            mConnectionProgressDialog.setMessage("Disconnecting ...");
            mConnectionProgressDialog.setCancelable(false);
            mConnectionProgressDialog.show();

            if (!mMiniDrone.disconnect()) {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onDestroy()
    {
        mMiniDrone.dispose();
        super.onDestroy();
    }

    private void initIHM() {
        mVideoView = (H264VideoView) findViewById(R.id.videoView);

        findViewById(R.id.emergencyBt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMiniDrone.emergency();
            }
        });

        mAnglieinc = (Button) findViewById(R.id.Aglieinc);
        mAnglieinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    time = 500;

                    mMiniDrone.setFlag((byte) 0 );

                    mMiniDrone.takeOff();

                    Thread.sleep(500);
                    for(int t=0;t<3; t++) {

                        mMiniDrone.setPitch((byte) 50);
                        mMiniDrone.setFlag((byte) 1);
                        Thread.sleep(1000);
                        mMiniDrone.setPitch((byte) 0);
                        mMiniDrone.setFlag((byte) 1);
                        Thread.sleep(time);
                        mMiniDrone.setGaz((byte) 0);
                        Thread.sleep(time);
                        mMiniDrone.setGaz((byte) -50);
                        Thread.sleep(time);
                        mMiniDrone.setGaz((byte) 0);
                        Thread.sleep(time);
                        mMiniDrone.setPitch((byte) 50);
                        mMiniDrone.setFlag((byte) 1);
                        Thread.sleep(1000);
                        mMiniDrone.setPitch((byte) 0);
                        mMiniDrone.setFlag((byte) 1);
                        Thread.sleep(time);
                        mMiniDrone.setGaz((byte) 50);
                        Thread.sleep(time);
                    }
                    mMiniDrone.setGaz((byte) 0);
                    Thread.sleep(500);
                    mMiniDrone.land();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mBowtie = (Button) findViewById(R.id.Bowtie);
        mBowtie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMiniDrone.setFlag((byte) 0 );

                    mMiniDrone.takeOff();
                    Thread.sleep(1000) ;
                    mMiniDrone.setGaz((byte) 50);
                    Thread.sleep(1000);
                    mMiniDrone.setGaz((byte) 0);
                    Thread.sleep(1000);
                    mMiniDrone.setPitch((byte) 50);
                    mMiniDrone.setFlag((byte) 1);
                    mMiniDrone.setGaz((byte) -30);
                    Thread.sleep(2000);
                    mMiniDrone.setPitch((byte) 0);
                    mMiniDrone.setFlag((byte) 1);
                    mMiniDrone.setGaz((byte) 30);
                    Thread.sleep(2000);
                    mMiniDrone.setPitch((byte) -50);
                    mMiniDrone.setFlag((byte) 1);
                    mMiniDrone.setGaz((byte) -30);
                    Thread.sleep(2000);
                    mMiniDrone.setGaz((byte) 0);
                    mMiniDrone.setPitch((byte) 0);
                    mMiniDrone.setFlag((byte) 1);
                    Thread.sleep(1000);
                    mMiniDrone.land();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mSpiral = (Button) findViewById(R.id.Spiral);
        mSpiral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMiniDrone.setFlag((byte)0);
                    mMiniDrone.takeOff();
                    Thread.sleep(1000) ;
                    mMiniDrone.setGaz((byte) 50);
                    mMiniDrone.setYaw((byte) 100);
                    Thread.sleep(2500);
                    mMiniDrone.setGaz((byte) 0);
                    mMiniDrone.setYaw((byte) 0);
                    Thread.sleep(500);
                    mMiniDrone.setPitch((byte) 50);
                    mMiniDrone.setFlag((byte) 1);
                    Thread.sleep( 2000);
                    mMiniDrone.setPitch((byte) 0);
                    mMiniDrone.setFlag((byte) 1);
                    Thread.sleep(500);
                    mMiniDrone.setGaz((byte) -50);
                    mMiniDrone.setYaw((byte) -100);
                    Thread.sleep( 2500);
                    mMiniDrone.land();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mpuppy = (Button) findViewById(R.id.puppy);
        mpuppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMiniDrone.setFlag((byte) 0 );

                    time = 500;
                    mMiniDrone.takeOff();


                        mMiniDrone.setPitch((byte) 50);
                        mMiniDrone.setFlag((byte) 1);
                        Thread.sleep(2000);
                        mMiniDrone.setPitch((byte) 0);
                        mMiniDrone.setFlag((byte) 1);
                        Thread.sleep(500);
                        mMiniDrone.setGaz((byte) 50);
                        Thread.sleep(500);
                        mMiniDrone.setYaw((byte) 100);
                        mMiniDrone.setGaz((byte) -50);
                        Thread.sleep(400);
                        mMiniDrone.land();
                    mMiniDrone.setGaz((byte) 0);
                    Thread.sleep(500);
                    mMiniDrone.land();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mgamble = (Button) findViewById(R.id.gamble);
        mgamble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMiniDrone.setFlag((byte) 0 );
                    mMiniDrone.takeOff();

                    Random rand = new Random();

                    mMiniDrone.setPitch((byte) 30);
                    mMiniDrone.setFlag((byte) 1);
                    Thread.sleep(rand.nextInt(5500) + 500);
                    mMiniDrone.land();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        insanity = (Button) findViewById(R.id.insanity);
        insanity.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           try {
                                               mMiniDrone.takeOff();
                                               mMiniDrone.setPitch((byte)-100);
                                               mMiniDrone.setFlag((byte) 1 );
                                               Thread.sleep(500);
                                               mMiniDrone.setPitch((byte)100);
                                               mMiniDrone.setFlag((byte) 1 );
                                               Thread.sleep(500);
                                               mMiniDrone.setPitch((byte)100);
                                               mMiniDrone.setFlag((byte) 1 );
                                               Thread.sleep(500);
                                               mMiniDrone.setPitch((byte)-100);
                                               mMiniDrone.setFlag((byte) 1 );
                                               Thread.sleep(500);
                                               mMiniDrone.setYaw((byte)20);
                                               Thread.sleep(500);
                                               mMiniDrone.setRoll((byte)100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)-100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)-100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)-100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)-100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)100);
                                               Thread.sleep(100);
                                               mMiniDrone.setRoll((byte)-100);
                                               Thread.sleep(100);
                                               mMiniDrone.land();
                                           } catch (InterruptedException e) {
                                               e.printStackTrace();
                                           }
                                       }
                                   });
        hit90s = (Button) findViewById(R.id.hit90s);
        hit90s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMiniDrone.setFlag((byte) 0 );
                    mMiniDrone.takeOff();

                    mMiniDrone.setPitch((byte) 40);
                    mMiniDrone.setFlag((byte) 1);
                    mMiniDrone.setYaw((byte) 50);
                    mMiniDrone.setGaz((byte) 20);
                    Thread.sleep(1000);

                    mMiniDrone.setFlag((byte) 0 );
                    mMiniDrone.land();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mTakeOffLandBt = (Button) findViewById(R.id.takeOffOrLandBt);
        mTakeOffLandBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (mMiniDrone.getFlyingState()) {
                    case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_LANDED:
                        mMiniDrone.takeOff();
                        break;
                    case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_FLYING:
                    case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_HOVERING:
                        mMiniDrone.land();
                        break;
                    default:
                }
            }
        });



        mBatteryLabel = (TextView) findViewById(R.id.batteryLabel);
    }

    private final MiniDrone.Listener mMiniDroneListener = new MiniDrone.Listener() {
        @Override
        public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {
            switch (state)
            {
                case ARCONTROLLER_DEVICE_STATE_RUNNING:
                    mConnectionProgressDialog.dismiss();
                    break;

                case ARCONTROLLER_DEVICE_STATE_STOPPED:
                    // if the deviceController is stopped, go back to the previous activity
                    mConnectionProgressDialog.dismiss();
                    finish();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onBatteryChargeChanged(int batteryPercentage) {
            mBatteryLabel.setText(String.format("%d%%", batteryPercentage));
        }

        @Override
        public void onPilotingStateChanged(ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {
            switch (state) {
                case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_LANDED:
                    mTakeOffLandBt.setText("Take off");
                    mTakeOffLandBt.setEnabled(true);
                    break;
                case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_FLYING:
                case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_HOVERING:
                    mTakeOffLandBt.setText("Land");
                    mTakeOffLandBt.setEnabled(true);
                    break;
                default:
                    mTakeOffLandBt.setEnabled(false);
            }
        }

        @Override
        public void onPictureTaken(ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {
            Log.i(TAG, "Picture has been taken");
        }

        @Override
        public void configureDecoder(ARControllerCodec codec) {
            mVideoView.configureDecoder(codec);
        }

        @Override
        public void onFrameReceived(ARFrame frame) {
            mVideoView.displayFrame(frame);
        }

        @Override
        public void onMatchingMediasFound(int nbMedias) {
            mDownloadProgressDialog.dismiss();

            mNbMaxDownload = nbMedias;
            mCurrentDownloadIndex = 1;

            if (nbMedias > 0) {
                mDownloadProgressDialog = new ProgressDialog(MiniDroneActivity.this, R.style.AppCompatAlertDialogStyle);
                mDownloadProgressDialog.setIndeterminate(false);
                mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDownloadProgressDialog.setMessage("Downloading medias");
                mDownloadProgressDialog.setMax(mNbMaxDownload * 100);
                mDownloadProgressDialog.setSecondaryProgress(mCurrentDownloadIndex * 100);
                mDownloadProgressDialog.setProgress(0);
                mDownloadProgressDialog.setCancelable(false);
                mDownloadProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMiniDrone.cancelGetLastFlightMedias();
                    }
                });
                mDownloadProgressDialog.show();
            }
        }

        @Override
        public void onDownloadProgressed(String mediaName, int progress) {
            mDownloadProgressDialog.setProgress(((mCurrentDownloadIndex - 1) * 100) + progress);
        }

        @Override
        public void onDownloadComplete(String mediaName) {
            mCurrentDownloadIndex++;
            mDownloadProgressDialog.setSecondaryProgress(mCurrentDownloadIndex * 100);

            if (mCurrentDownloadIndex > mNbMaxDownload) {
                mDownloadProgressDialog.dismiss();
                mDownloadProgressDialog = null;
            }
        }
    };
}
