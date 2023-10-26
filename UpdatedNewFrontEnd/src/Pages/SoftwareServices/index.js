import { Space, Typography, Tabs } from "antd";
import { useState } from "react";
import { Tab } from "@mui/material";
import SoftwareChangePlanForm from "./SoftwareChangePlanForm";
import RenewSoftwareForm from "./RenewSoftwareForm";
import SoftwareAnalysisForm from "./SoftwareAnalysisForm";

function Services() {
  const [activeTab, setActiveTab] = useState("softwareRenewal"); // Set the default tab to "softwareRenewal"

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Services</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="softwareRenewal" tab="Renew software" />
        <Tab key="softwareChangePlan" tab="Change Plan" />
        <Tab key="softwareAnalysis" tab="Software Analysis" />
      </Tabs>
      {activeTab === "softwareRenewal" && <RenewSoftwareForm />}
      {activeTab === "softwareChangePlan" && <SoftwareChangePlanForm />}
        {activeTab === "softwareAnalysis" && <SoftwareAnalysisForm />}

    </Space>
  );
}

export default Services;
