import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.hipparchus.util.FastMath;
import org.hipparchus.util.MathUtils;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.frames.FactoryManagedFrame;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.propagation.AdditionalStateProvider;
import org.orekit.propagation.Propagator;
import org.orekit.propagation.analytical.tle.SGP4;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.TimeStampedPVCoordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File orData = new File("C:/Users/matys/Desktop/PROGRAMY_DR/SGP4_Propagacja_2LE/dane");
        DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
        manager.addProvider(new DirectoryCrawler(orData));

        File f = new File("C:/Users/matys/Desktop/PROGRAMY_DR/SGP4_Propagacja_2LE/dane/cat_new.tle");

        Scanner skan = new Scanner(f);

        String tle_1a = skan.nextLine();
        String tle_2a = skan.nextLine();
        TLE tle1 = new TLE(tle_1a, tle_2a);
        AbsoluteDate date_sat1 = tle1.getDate();
         skan = new Scanner(f);
        while (skan.hasNextLine()) {

            String tle_1 = skan.nextLine();
            String tle_2 = skan.nextLine();

            TLE tle = new TLE(tle_1, tle_2);
            AbsoluteDate date = tle.getDate();
           // System.out.println(date);
            FactoryManagedFrame ITRF = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
            OneAxisEllipsoid earth = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS, Constants.WGS84_EARTH_FLATTENING, ITRF);
            Frame inertialFrame = FramesFactory.getEME2000();

            Propagator propagator = SGP4.selectExtrapolator(tle);
            String nazwa = tle.getSatelliteNumber() + ".txt";
            PrintWriter output2 = new PrintWriter("C:/Users/matys/Desktop/PROGRAMY_DR/SGP4_Propagacja_2LE/dane/" + nazwa);
            AbsoluteDate extrapDate = new AbsoluteDate(String.valueOf(tle.getDate()), TimeScalesFactory.getUTC());
           // extrapDate = new AbsoluteDate(2022, 3, 29, 22, 31, 33.0, TimeScalesFactory.getUTC());
            extrapDate = new AbsoluteDate(String.valueOf(date_sat1.getDate()), TimeScalesFactory.getUTC());
            AbsoluteDate finalDate = extrapDate.shiftedBy(60.0 * 60 * 24 * 3.5); //seconds
          //  System.out.println(extrapDate);
          //  System.out.println(finalDate);
            while (extrapDate.compareTo(finalDate) <= 0.0) {
                extrapDate = extrapDate.shiftedBy(30.0);
                TimeStampedPVCoordinates pv = propagator.getPVCoordinates(extrapDate, inertialFrame);

                AbsoluteDate date1 = pv.getDate();
                Vector3D pos = pv.getPosition();
                Vector3D vel = pv.getVelocity();
//               System.out.format(Locale.US, "%s %12.8f %12.6f %12.6f %12.6f %12.6f %12.6f %12.6f%n",
//                       pv.getDate(),
//                       pv.getDate().durationFrom(extrapDate) / 86400.0,
//                       pv.getPosition().getX() * 0.001, //"position along X (km)"
//                       pv.getPosition().getY() * 0.001, //"position along Y (km)"
//                       pv.getPosition().getZ() * 0.001, //"position along Y (km)"
//                       pv.getVelocity().getX() * 0.001, //"velocity along X (km/s)"
//                       pv.getVelocity().getY() * 0.001, //"velocity along Y (km/s)"
//                       pv.getVelocity().getZ() * 0.001);


                output2.format(Locale.US, "%s %12.8f %12.6f %12.6f %12.6f %12.6f %12.6f %12.6f%n",
                        pv.getDate(),
                        pv.getDate().durationFrom(tle1.getDate()),
                        pv.getPosition().getX() * 0.001, //"position along X (km)"
                        pv.getPosition().getY() * 0.001, //"position along Y (km)"
                        pv.getPosition().getZ() * 0.001, //"position along Y (km)"
                        pv.getVelocity().getX() * 0.001, //"velocity along X (km/s)"
                        pv.getVelocity().getY() * 0.001, //"velocity along Y (km/s)"
                        pv.getVelocity().getZ() * 0.001); //"velocity along Z (km/s)"


            }
            output2.close();


            // System.out.println(tle + "\n");


        }
    }
}