import React, { useState, useEffect } from 'react';

const NetworkDeviceRMAForm = () => {
    const [networkDeviceId, setNetworkDeviceId] = useState('');
    const [actionType, setActionType] = useState('');
    const [amount, setAmount] = useState('');
    const [dateOfAction, setDateOfAction] = useState('');
    const [reason, setReason] = useState('');

    const [networkDevices, setNetworkDevices] = useState([]);

    const resetForm = () => {
        setNetworkDeviceId('');
        setActionType('');
        setAmount('');
        setDateOfAction('');
        setReason('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const rmaData = {
            networkDevice: {
                id: networkDeviceId,
            },
            networkDeviceRMA: {
                actionType,
                amount,
                dateOfAction,
                reason,
            },
        };

        try {
            const response = await fetch('http://localhost:8080/api/setRMA', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(rmaData),
            });

            if (response.ok) {
                const data = await response.json();
                alert(data.responseBody);
            } else {
                alert('Failed to add network device RMA. Please check the network device ID.');
            }
        } catch (error) {
            alert('An error occurred while adding network device RMA.');
        }

        resetForm();
        console.log(rmaData);
    }

    useEffect(() => {
        fetch('http://localhost:8080/api/allHardware')
            .then((response) => response.json())
            .then((data) => {
                setNetworkDevices(data);
            })
            .catch((error) => console.error('Error fetching network devices: ', error));
    }

    );

    return (
        <div className="form-container">
            <h2>Set Network Device RMA</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Network Device ID:
                    <select
                        value={networkDeviceId}
                        onChange={(e) => setNetworkDeviceId(e.target.value)}
                    >
                        <option value="">Select a network device</option>
                        {networkDevices.map((networkDevice) => (
                            <option key={networkDevice.id} value={networkDevice.id}>
                                {networkDevice.id}
                            </option>
                        ))}
                    </select>
                </label>
                <label>
                    Action Type:
                    <input
                        type="text"
                        value={actionType}
                        onChange={(e) => setActionType(e.target.value)}
                    />
                </label>
                <label>
                    Amount:
                    <input
                        type="text"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                </label>
                <label>
                    Date of Action:
                    <input
                        type="date"
                        value={dateOfAction}
                        onChange={(e) => setDateOfAction(e.target.value)}
                    />
                </label>
                <label>
                    Reason:
                    <input
                        type="text"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                    />
                </label>
                <button type="submit">Set Network Device RMA</button>
            </form>
        </div>
    );
};

export default NetworkDeviceRMAForm;
