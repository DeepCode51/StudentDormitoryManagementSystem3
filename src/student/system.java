//增加数据：点击增加按钮会出现一空白行，填写完点击保存即可保存（更新）数据：直接在表中进行相应的操作，再点击保存即可，删除数据：选择某行，点击删除按钮即可
//系统管理界面代码
package student;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class system extends JFrame implements ChangeListener, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;//和land中的一样固定，后面很多前面有注释，可以翻翻前面
    static SQL sq = new SQL();
    JFrame frame = new JFrame();
    JTabbedPane paneParent = new JTabbedPane();
    JPanel paneWelcome = new JPanel();
    JPanel paneStudent = new JPanel();
    JPanel paneDormitory = new JPanel();
    JPanel paneButton = new JPanel();
    JLabel labelWelcome = new JLabel("学生宿舍管理系统");
    JLabel labelWelcome2 = new JLabel("您的身份为系统管理员");
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane s;
    JButton buttonSave = new JButton("保存");//Jbutton都是按钮
    JButton buttonDelete = new JButton("删除");
    JButton buttonIncrease = new JButton("增加");
    Vector<String> dataTitle = new Vector<String>();
    Vector<String> TitleDormitor = new Vector<String>();
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();

    public system() {
        frame.setSize(700, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        //选项卡
        frame.add(paneParent);
        paneParent.setBounds(0, 0, 500, 600);
        frame.add(paneButton);
        paneButton.setBounds(510, 0, 200, 600);
        paneParent.addTab("学生管理系统", null, paneWelcome, null);
        paneParent.addTab("学生信息", null, paneStudent, "学生信息");
        paneParent.addTab("宿舍信息", null, paneDormitory, "宿舍信息");
        //欢迎页
        paneWelcome.setLayout(null);
        paneWelcome.add(labelWelcome);
        labelWelcome.setBounds(50, 150, 500, 100);
        labelWelcome.setFont(new java.awt.Font("Dialog", 1, 40));
        labelWelcome.setForeground(Color.blue);
        paneWelcome.add(labelWelcome2);
        labelWelcome2.setBounds(60, 300, 500, 40);
        labelWelcome2.setFont(new java.awt.Font("Dialog", 1, 30));
        labelWelcome2.setForeground(Color.BLUE);
        //表格
        dataTitle.add("学号");
        dataTitle.add("姓名");
        dataTitle.add("性别");
        dataTitle.add("专业");
        dataTitle.add("宿舍号");
        dataTitle.add("入住时间");
        TitleDormitor.add("宿舍号");
        TitleDormitor.add("宿舍电话");
        tableModel = new DefaultTableModel(data, dataTitle);
        table = new JTable(tableModel);
        s = new JScrollPane(table);
        paneStudent.setLayout(null);
        paneStudent.add(s);
        s.setBounds(5, 10, 500, 510);
        //按钮
        paneButton.setLayout(null);
        paneButton.add(buttonSave);
        buttonSave.setBounds(25, 30, 100, 30);
        paneParent.addChangeListener(this);
        paneButton.add(buttonDelete);
        buttonDelete.setBounds(25, 80, 100, 30);
        paneButton.add(buttonIncrease);
        buttonIncrease.setBounds(25, 130, 100, 30);
        buttonSave.addActionListener(this);
        buttonDelete.addActionListener(this);
        buttonIncrease.addActionListener(this);
        buttonDelete.setVisible(false);
        buttonSave.setVisible(false);
        buttonIncrease.setVisible(false);
    }

    //选项卡事件
    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        if (((JTabbedPane) e.getSource()).getSelectedIndex() == 0) {
            buttonDelete.setVisible(false);
            buttonSave.setVisible(false);
            buttonIncrease.setVisible(false);
        }
        if (((JTabbedPane) e.getSource()).getSelectedIndex() == 1) {
            buttonDelete.setVisible(true);
            buttonSave.setVisible(true);
            buttonIncrease.setVisible(true);
            paneStudent.add(s);
            data = sq.query("studentinfo", 6);
            //System.out.println(data);
            tableModel.setDataVector(data, dataTitle);
        }
        if (((JTabbedPane) e.getSource()).getSelectedIndex() == 2) {
            buttonDelete.setVisible(true);
            buttonSave.setVisible(true);
            buttonIncrease.setVisible(true);
            paneDormitory.setLayout(null);
            paneDormitory.add(s);
            s.setBounds(5, 10, 500, 510);
            data = sq.query("dormitory", 2);
            //System.out.println(data);
            tableModel.setDataVector(data, TitleDormitor);
        }

    }

    //按钮事件
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        String tableName = null;
        String key1 = null;
        if ((JButton) e.getSource() == buttonDelete) {
            if (paneParent.getSelectedIndex() == 1) {
                tableName = "studentinfo";
                key1 = "sno";
            }
            if (paneParent.getSelectedIndex() == 2) {
                tableName = "dormitory";
                key1 = "dno";
            }
            //可以随意建表，我只写了两个表
            int row = table.getSelectedRow();
            if (row != -1) {
                String key2 = (String) tableModel.getValueAt(row, 0);
                int result = JOptionPane.showConfirmDialog(null, "确定要删除吗？", "请确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String sql = "delete from " + tableName + " where " + key1 + "='" + key2+ "'";
                    int num = sq.delete(sql);
                    if (num > 0) {
                        tableModel.removeRow(row);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "请选择要删除的行！", "提示：", JOptionPane.ERROR_MESSAGE);
            }
        }
        //保存
        if ((JButton) e.getSource() == buttonSave) {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            int result = JOptionPane.showConfirmDialog(null, "请确认数值已经更改，否则保存无效", "请确认", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int row = table.getRowCount();
                int column = table.getColumnCount();
                String[][] valueRow = new String[row][column];
                String[] sqlvalue = new String[row];
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        valueRow[i][j] = String.valueOf(table.getValueAt(i, j));
                    }
                }
                if (paneParent.getSelectedIndex() == 1) {
                    for (int i = 0; i < row; i++) {
                        String sql = "insert into studentinfo" + " values ('" + valueRow[i][0].toString() + "','" + valueRow[i][1].toString() + "','" + valueRow[i][2].toString() + "','" + valueRow[i][3].toString() + "','" + valueRow[i][4].toString() + "','" + valueRow[i][5].toString() + "')";
                        sqlvalue[i] = sql.toString();
                    }
                    data = sq.Save(sqlvalue, "studentinfo", row, column);
                    tableModel.setDataVector(data, dataTitle);
                }
                if (paneParent.getSelectedIndex() == 2) {
                    for (int i = 0; i < row; i++) {
                        String sql = "insert into dormitory" + " values ('" + valueRow[i][0].toString() + "','" + valueRow[i][1].toString() + "')";
                        sqlvalue[i] = sql.toString();
                    }
                    data = sq.Save(sqlvalue, "dormitory", row, column);
                    tableModel.setDataVector(data, TitleDormitor);
                }
            }
        }
        //增加
        if ((JButton) e.getSource() == buttonIncrease) {
            tableModel.addRow(new Vector<>());
        }
    }
}
