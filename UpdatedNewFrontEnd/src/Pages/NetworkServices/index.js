import { Space, Typography, Tabs } from "antd";
import { useState } from "react";
import { Tab } from "@mui/material";
import NetworkDeviceRMAForm from "./NetworkDeviceRMAForm";
import NetworkDeviceAnalysisForm from "./NetworkDeviceAnalysisForm";


function NetworkServices(){
    const [activeTab, setActiveTab] = useState("NetworkRMA");
    
    const handleChangeTab = (key) => {
        setActiveTab(key);
    };

    const containerStyle = {
        padding: '20px', // Add padding to the container
        backgroundColor: '#f0f0f0', // Add a background color
      };
    
    return (
        <div style={containerStyle}>
       
        <Typography.Title level={4}>Network Services</Typography.Title>
        <Tabs activeKey={activeTab} onChange={handleChangeTab}>
            <Tab key="NetworkRMA" tab="RMA" />
            <Tab key="NetworkAnalysis" tab="Network Analysis" />


        </Tabs>
        {activeTab === "NetworkRMA" && <NetworkDeviceRMAForm />}
        {activeTab === "NetworkAnalysis" && <NetworkDeviceAnalysisForm />}
        </div>
    );
}

export default NetworkServices;



