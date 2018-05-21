<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/pure-min.css">
    <link rel="stylesheet" href="/css/couplet.css">
  </head>

  <body>
    <div>
      <input id="inputFirstCouplet" size="10" name="firstCouplet" type="text" />

      <table id="preview">
        <tbody>
          <tr>
            <td colspan="3">
              <table>
                <tbody>
                  <tr>
                    <td style="width: 16px;"></td>

                    <!-- 上联 -->
                    <td>
                      <div id="firstCouplet">
                        <table style="margin-left: auto; margin-right: auto;">
                          <tbody>
                          </tbody>
                        </table>
                      </div>
                    </td>

                    <td style="width: 64px;"></td>

                    <!-- 下联 -->
                    <td>
                      <div id="secondCouplet">
                        <table style="margin-left: auto; margin-right: auto;">
                          <tbody>
                          </tbody>
                        </table>
                      </div>
                    </td>

                    <td style="width: 16px;"></td>
                  </tr>
                </tbody>
              </table>
            </td>
          </tr>
        </tbody>
      </table>
      
    </div>
    
  </body>

  <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
  <script type="text/javascript" src="js/couplet.js"></script>

</html>
