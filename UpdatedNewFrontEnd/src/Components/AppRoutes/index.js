import { BrowserRouter, Route, Routes } from "react-router-dom";
import Manufacturer from "../../Pages/Manufacturer";
import Dashboard from "../../Pages/Dashbaord";
import NetworkDevices from "../../Pages/NetworkDevices";
import SoftwareDevices from "../../Pages/SoftwareDevices";
import TotalSoftwareDevices from "../../Pages/SoftwareDevices/TotalSoftwareDevices";
import LessThan45Days from "../../Pages/SoftwareDevices/LessThan45Days";
import LessThanZeroDays from "../../Pages/SoftwareDevices/LessThanZeroDays";
import MoreThan45Days from "../../Pages/SoftwareDevices/MoreThan45days";
import SoftwareForm from "../../Pages/SoftwareDevices/SoftwareForm";
import ManufacturerForm from "../../Pages/Manufacturer/ManufacturerForm";
import SoftwareRenewalForm from "../../Pages/SoftwareDevices/SoftwareRenewalForm";




function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />}></Route>
      <Route path="/SoftwareDevices" element={<SoftwareDevices />}></Route>
      <Route path="/NetworkDevices" element={<NetworkDevices />}></Route>
      <Route path="/Manufacturer" element={<Manufacturer />}></Route>
      <Route path='/TotalSoftwareDevices' element={<TotalSoftwareDevices />}></Route>
      <Route path='/LessThan45Days' element={<LessThan45Days />}></Route>
      <Route path='/MoreThan45Days' element={<MoreThan45Days />}></Route>
      <Route path='/LessThanZeroDays' element={<LessThanZeroDays />}></Route>
      <Route path='/softwareForm' element={<SoftwareForm />}></Route>
      <Route path='/manufacturerForm' element={<ManufacturerForm />}></Route>
      <Route path='/softwareRenewalForm' element={<SoftwareRenewalForm />}></Route>
    </Routes>
  );
}
export default AppRoutes;
